package com.example.meetyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityRegisterationBinding;

public class RegisterationActivity extends AppCompatActivity {

    ActivityRegisterationBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterationActivity.this, R.color.main));

        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(v -> finish());

        binding.signUpButton.setOnClickListener(v -> {
            String email = binding.mailText.getText().toString().trim();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();

            if (isValidEmail(email)) {
                if (password.length() < 8) {
                    Toast.makeText(RegisterationActivity.this, "Длина пароля должна быть больше 8 символов", Toast.LENGTH_SHORT).show();
                } else if (!password.matches(".*[A-Z].*")) {
                    Toast.makeText(RegisterationActivity.this, "Пароль должен содержать хотя бы одну заглавную букву", Toast.LENGTH_SHORT).show();
                } else if (password.contains(" ")) {
                    Toast.makeText(RegisterationActivity.this, "Пароль не должен содержать пробелы", Toast.LENGTH_SHORT).show();
                } else if (!confirmPassword.equals(password)) {
                    Toast.makeText(RegisterationActivity.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                } else {
                    // Проверяем, что такой email не зарегистрирован ранее
                    if (!databaseHelper.checkEmail(email)) {
                        boolean insert = databaseHelper.insertData(email, password);
                        if (insert) {
                            Toast.makeText(RegisterationActivity.this, "Успешная регистрация!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterationActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterationActivity.this, "Пользователь с таким email уже зарегистрирован!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // Некорректный формат электронной почты
                Toast.makeText(RegisterationActivity.this, "Некорректный формат электронной почты", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
