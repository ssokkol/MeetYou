package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityChangeGenderBinding;

public class ChangeGenderActivity extends AppCompatActivity {

    ActivityChangeGenderBinding binding;
    DatabaseHelper databaseHelper;
    long userID;

    public boolean isFemale = false;
    public boolean isMale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        // Получаем userID текущего пользователя из SharedPreferences
        userID = getUserID();

        binding.femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFemale) {
                    isMale = false;
                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                } else {
                    isFemale = true;
                    isMale = false;

                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                }
            }
        });

        binding.maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMale) {
                    isMale = false;
                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                } else {
                    isFemale = false;
                    isMale = true;

                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));// Use ContextCompat.getColor() to get color resources
                }
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMale && !isFemale)
                {
                    Toast.makeText(ChangeGenderActivity.this, "Change your gender", Toast.LENGTH_SHORT).show();
                } else if (isFemale || isMale)
                {
                    Intent intent = new Intent(ChangeGenderActivity.this, CreateBioActivity.class);
                    startActivity(intent);

                }
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private long getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }
}
