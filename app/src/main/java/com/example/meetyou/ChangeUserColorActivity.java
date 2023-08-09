package com.example.meetyou;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChangeUserColorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeUserColorActivity extends AppCompatActivity {

    ActivityChangeUserColorBinding binding;
    String inputColor;
    boolean isConfirmActivated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeUserColorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        getUserColor(new OnColorReceivedListener() {
            @Override
            public void onColorReceived(String color) {
                binding.colorView.setBackgroundColor(android.graphics.Color.parseColor(color));
            }
        });

        // Устанавливаем максимальное количество символов в EditText в 7
        binding.newColorEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});

        // Добавляем слушатель изменения текста для EditText
        binding.newColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Remove the TextWatcher temporarily to prevent infinite loops
                binding.newColorEditText.removeTextChangedListener(this);

                // Получаем текст из EditText и удаляем запрещенные символы
                inputColor = "#" + removeForbiddenCharacters(binding.newColorEditText.getText().toString());

                // Устанавливаем новое значение для EditText
                binding.newColorEditText.setText(inputColor);
                // Устанавливаем курсор в конец строки
                binding.newColorEditText.setSelection(inputColor.length());

                // Проверяем длину строки, чтобы убедиться, что после "#" есть 6 символов (цвет в формате #RRGGBB)
                if (inputColor.length() == 7) {
                    // Устанавливаем цвет для colorView
                    binding.colorView.setBackgroundColor(android.graphics.Color.parseColor(inputColor));
                    changeButtonColor(true);
                } else if (inputColor.length() < 7) {
                    // Если длина строки меньше 7 символов, устанавливаем цвет по умолчанию
                    binding.colorView.setBackgroundColor(getColor(R.color.neutral_dark_gray));
                    changeButtonColor(false);
                }

                // Add the TextWatcher back after making the changes
                binding.newColorEditText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConfirmActivated){
                    saveUserColor(inputColor);
                    finish();
                } else {
                    NotificationHelper.showCustomNotification(ChangeUserColorActivity.this, null, "Enter new color in HEX format", null, 0, 0, 0, 0);
                }
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String removeForbiddenCharacters(String input) {
        // Регулярное выражение для отфильтровки символов
        String pattern = "[^0-9A-Fa-f]";

        // Создаем объект Pattern для регулярного выражения
        Pattern regexPattern = Pattern.compile(pattern);

        // Создаем объект Matcher для нахождения совпадений
        Matcher matcher = regexPattern.matcher(input);

        // Заменяем все найденные совпадения на пустую строку
        String result = matcher.replaceAll("");

        return result;
    }

    private void changeButtonColor(boolean parameter){
        if(parameter){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
            isConfirmActivated = true;
        }else{
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
            isConfirmActivated = false;
        }
    }

    private void getUserColor(final OnColorReceivedListener listener) {
        DatabaseReference colorRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("color");
        colorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String color = snapshot.getValue(String.class);
                if (color != null) {
                    listener.onColorReceived(color);
                } else {
                    listener.onColorReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onColorReceived(" ");
            }
        });
    }
    interface OnColorReceivedListener {
        void onColorReceived(String color);
    }

    private void saveUserColor(String color){
        Users.updateUserColor(getUID(), color);
    }


    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
