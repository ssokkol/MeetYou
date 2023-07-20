package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class StartAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acivity);

        getWindow().setStatusBarColor(ContextCompat.getColor(StartAcivity.this, R.color.main));

        AppCompatButton signUpButton = findViewById(R.id.sign_up_button);
        AppCompatButton signInButton = findViewById(R.id.sign_in_button);

        if (isUserLoggedIn()) {
            startActivity(new Intent(StartAcivity.this, MainActivity.class));
        } else {
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartAcivity.this, RegisterationActivity.class);
                    startActivity(intent);
                }
            });

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StartAcivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void finishStartActivity(){
        finish();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");
        String userPassword = sharedPreferences.getString("password", "");

        // Возможно, вам также потребуется дополнительная проверка на валидность пользовательских данных
        return !userEmail.isEmpty() && !userPassword.isEmpty();
    }
}
