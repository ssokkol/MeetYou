package com.example.meetyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @NonNull ActivityMainBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

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