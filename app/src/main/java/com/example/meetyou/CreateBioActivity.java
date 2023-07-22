package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityCreateBioBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateBioActivity extends AppCompatActivity {

    ActivityCreateBioBinding binding;
    DatabaseHelper databaseHelper;
    long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCreateBioBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(CreateBioActivity.this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        // Получаем userID текущего пользователя из SharedPreferences
        userID = getUserID();

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ничего не делаем перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Счетчик букав
                int charCount = charSequence.length();
                binding.charsCount.setText(charCount + "/150");
                if(charCount > 150)
                {
                    binding.charsCount.setTextColor(getResources().getColor(R.color.red));
                } else if (charCount <= 150)
                {
                    binding.charsCount.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ничего не делаем после изменения текста
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Херь всякая про типа
                String bio = binding.bio.getText().toString();
                String name = binding.enterName.getText().toString();
                // Вычисление возраста
                String birthYearStr = binding.enterBirthYear.getText().toString();
                String birthMonthStr = binding.enterBirthMonth.getText().toString();
                String birthDayStr = binding.enterBirthDay.getText().toString();

                // Ограничение на имя (не более 30 символов)
                if (name.length() > 30) {
                    Toast.makeText(CreateBioActivity.this, R.string.name_chars_count_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!name.matches("[a-zA-Zа-яА-Я\\s]+")) {
                    Toast.makeText(CreateBioActivity.this, R.string.name_chars_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Ограничение на био (не более 150 символов)
                else if (bio.length() > 150) {
                    Toast.makeText(CreateBioActivity.this, R.string.bio_chars_message, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!birthYearStr.isEmpty() && !birthMonthStr.isEmpty() && !birthDayStr.isEmpty()) {
                    int birthYear = Integer.parseInt(birthYearStr);
                    int birthMonth = Integer.parseInt(birthMonthStr);
                    int birthDay = Integer.parseInt(birthDayStr);

                    if (calculateAge(birthYear, birthMonth, birthDay) > 72) {
                        Toast.makeText(CreateBioActivity.this, R.string.old_ages_message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Проверка, что пользователь не младше 18 лет
                    if (isUnderage(birthYear, birthMonth, birthDay)) {
                        Toast.makeText(CreateBioActivity.this, R.string.eighteen_years_message, Toast.LENGTH_SHORT).show();
                    } else if (birthMonth <= 0 || birthMonth > 12)
                    {
                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    } else if (birthDay <= 0 || birthDay > 31) {
                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    } else if(birthMonth == 2 && birthDay > 29){
                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        long result = databaseHelper.insertBio(userID, name, calculateAge(birthYear, birthMonth, birthDay), bio);
                        if (result != -1) {
                            Toast.makeText(CreateBioActivity.this, R.string.data_saved_successfully_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateBioActivity.this, ChangeParametersActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CreateBioActivity.this, R.string.error_data_saving_message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(CreateBioActivity.this, R.string.enter_date_in_full_message, Toast.LENGTH_SHORT).show();
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

    // Проверка, что пользователю меньше 18 лет
    private boolean isUnderage(int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month - 1, day);

        // Проверка на то, что дата рождения уже была в этом году
        if (birthDate.after(currentDate)) {
            return true;
        }

        // Проверка, что возраст меньше 18 лет
        currentDate.add(Calendar.YEAR, -18);
        return (birthDate.after(currentDate));
    }

    private long getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }

    private int calculateAge(int birthYear, int birthMonth, int birthDay) {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }
        return age;
    }
}
