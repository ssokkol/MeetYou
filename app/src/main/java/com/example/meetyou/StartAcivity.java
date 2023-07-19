package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import android.content.Intent;
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
