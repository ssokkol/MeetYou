package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем сохраненные данные о пользователе
        String userEmail = getUserEmail();
        String userPassword = getUserPassword();

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.main));
    }

    // Метод для получения почты текущего пользователя из SharedPreferences
    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    // Метод для получения пароля текущего пользователя из SharedPreferences
    private String getUserPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }
}