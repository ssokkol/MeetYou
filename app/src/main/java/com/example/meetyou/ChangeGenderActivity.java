package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityChangeGenderBinding;

public class ChangeGenderActivity extends AppCompatActivity {

    ActivityChangeGenderBinding binding;
    DatabaseHelper databaseHelper;
    public boolean isFemale = false;
    public boolean isMale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main)); // Use "this" instead of "ChangeGenderActivity.this"

        binding.woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFemale) {
                    isMale = false;
                    binding.woman.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.woman.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.man.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.man.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.acceptButton.setBackgroundResource(R.drawable.button_background_gray);
                    binding.acceptButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                } else {
                    isFemale = true;
                    isMale = false;

                    binding.woman.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.woman.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white)); // Use ContextCompat.getColor() to get color resources
                    binding.man.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.man.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black)); // Use ContextCompat.getColor() to get color resources
                    binding.acceptButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.acceptButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white)); // Use ContextCompat.getColor() to get color resources
                }
            }
        });

        binding.man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMale) {
                    isMale = false;
                    binding.woman.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.woman.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.man.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.man.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.acceptButton.setBackgroundResource(R.drawable.button_background_gray);
                    binding.acceptButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                } else {
                    isFemale = false;
                    isMale = true;

                    binding.woman.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.woman.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.man.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.man.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                    binding.acceptButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.acceptButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));// Use ContextCompat.getColor() to get color resources
                }
            }
        });
    }
}
