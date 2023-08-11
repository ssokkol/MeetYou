package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acivity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.main));

        if(getRememberMe()){
            Intent intent = new Intent(StartActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        AppCompatButton signUpButton = findViewById(R.id.sign_up_button);
        AppCompatButton signInButton = findViewById(R.id.sign_in_button);
        AppCompatButton FAQButton = findViewById(R.id.forgot_password_button);

        if (isUserLoggedIn()) {
            startActivity(new Intent(StartActivity.this, SignInActivity.class));
        } else {
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StartActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });

            FAQButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationHelper.showCustomNotification(StartActivity.this, null, null, null, 0, 0, 0, 0);
                }
            });
        }
    }

    private boolean getRememberMe(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("rememberMe", false);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");
        String userPassword = sharedPreferences.getString("password", "");
        boolean rememberMeChecked = sharedPreferences.getBoolean("remember_me", false);

        return rememberMeChecked && !userEmail.isEmpty() && !userPassword.isEmpty();
    }


}
