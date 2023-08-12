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
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acivity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.main));

        mAuth = FirebaseAuth.getInstance();

        AppCompatButton signUpButton = findViewById(R.id.sign_up_button);
        AppCompatButton signInButton = findViewById(R.id.sign_in_button);
        AppCompatButton FAQButton = findViewById(R.id.forgot_password_button);

        if(getRememberMe()){
            if (!getEmail().isEmpty() && !getPassword().isEmpty()) {
                mAuth.signInWithEmailAndPassword(getEmail(), getPassword());
                Intent intent = new Intent(StartActivity.this, OwnProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }else{
            NotificationHelper.showCustomNotification(StartActivity.this, "DEBUG", "I wasn't remembered", "FUCK OFF",0,0,0,0);
        }

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

    private boolean getRememberMe(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me", false);
    }

    private String getEmail(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    private String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }

    private void saveUID(String UID) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UID", UID);
        editor.apply();
    }

    public String sanitizeEmail(String email) {
        String sanitizedEmail = email.replaceAll("[.@#$\\[\\]]", "");
        return sanitizedEmail;
    }
}
