package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;;

public class RegisterationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterationActivity.this, R.color.main));

        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterationActivity.this, StartAcivity.class);
                startActivity(intent);
            }
        });

        AppCompatButton signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.mail_text);
                String email = emailEditText.getText().toString().trim();

                if (isValidEmail(email)) {
                    //здесь будет код регистрации
                    Toast.makeText(RegisterationActivity.this, "Успешная регистрация!", Toast.LENGTH_SHORT).show();
                } else {
                    // Нздесь будет кастомная нотифайка
                    Toast.makeText(RegisterationActivity.this, "Некорректный формат электронной почты", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}