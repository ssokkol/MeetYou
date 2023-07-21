package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
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
                    Toast.makeText(RegisterationActivity.this, R.string.wrong_password_lenght_message, Toast.LENGTH_SHORT).show();
                } else if (!password.matches(".*[A-Z].*")) {
                    Toast.makeText(RegisterationActivity.this, R.string.wrong_password_chars_format_message, Toast.LENGTH_SHORT).show();
                } else if (password.contains(" ")) {
                    Toast.makeText(RegisterationActivity.this, R.string.password_has_spaces_message, Toast.LENGTH_SHORT).show();
                } else if (!confirmPassword.equals(password)) {
                    Toast.makeText(RegisterationActivity.this, R.string.wrong_confirm_password_message, Toast.LENGTH_SHORT).show();
                } else {
                    if (!databaseHelper.checkEmail(email)) {
                        long userID = databaseHelper.insertData(email, password);
                        if (userID != -1) {
                            saveUserInfo(email, password);
                            saveUserID(userID);
                            Toast.makeText(RegisterationActivity.this, R.string.success_registration_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterationActivity.this, ChangeGenderActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterationActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterationActivity.this, R.string.email_was_used_message, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // Некорректный формат электронной почты
                Toast.makeText(RegisterationActivity.this, R.string.wrong_mail_format_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveUserInfo(String email, String password) {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void saveUserID(long userID) {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("userID", userID);
        editor.apply();
    }
}
