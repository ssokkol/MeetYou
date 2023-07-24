package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHelper = new DatabaseHelper(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.main));

        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.mailText.getText().toString().trim();
                String password = binding.password.getText().toString();

                if (!isValidEmail(email)) {
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_mail_format_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(SignInActivity.this, R.string.wrong_mail_format_message, Toast.LENGTH_SHORT).show();
                } else if (email.equals("") || password.equals("")) {
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.zero_input_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(SignInActivity.this, R.string.zero_input_message, Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
                    if (checkCredentials) {
                        saveUserCredentials(email, password);
                        Toast.makeText(SignInActivity.this, R.string.success_sign_in_message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_password_or_mail_message), getString(R.string.close), 0, 0, 0,0);
                        //Toast.makeText(SignInActivity.this, R.string.wrong_password_or_mail_message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveUserCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }
}
