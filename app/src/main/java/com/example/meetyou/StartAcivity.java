package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class StartAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acivity);

        getWindow().setStatusBarColor(ContextCompat.getColor(StartAcivity.this, R.color.main));
    }
}