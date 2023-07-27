package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityChooseFindGenderBinding;

public class ChooseFindGenderActivity extends AppCompatActivity {

    ActivityChooseFindGenderBinding binding;

    private boolean isAny = false;
    private boolean isMale = false;
    private boolean isFemale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseFindGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        binding.buttonAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAny = true;
                isMale = false;
                isFemale = false;
                binding.buttonAny.setBackgroundResource(R.drawable.btn_bg_blue);
                binding.buttonAny.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));
                binding.buttonFemale.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonFemale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.buttonMale.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonMale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));

            }
        });

        binding.buttonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAny = false;
                isMale = true;
                isFemale = false;
                binding.buttonMale.setBackgroundResource(R.drawable.btn_bg_blue);
                binding.buttonMale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));
                binding.buttonFemale.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonFemale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.buttonAny.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonAny.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));

            }
        });

        binding.buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAny = false;
                isMale = false;
                isFemale = true;
                binding.buttonFemale.setBackgroundResource(R.drawable.btn_bg_blue);
                binding.buttonFemale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));
                binding.buttonAny.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonAny.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.buttonMale.setBackgroundResource(R.drawable.btn_bg_gray);
                binding.buttonMale.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.black));
                binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseFindGenderActivity.this, android.R.color.white));

            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFemale || isMale || isAny){
                    Intent intent = new Intent(ChooseFindGenderActivity.this, ChangeFindTargetActivity.class);
                    startActivity(intent);
                } else
                {
                    NotificationHelper.showCustomNotification(ChooseFindGenderActivity.this, null, "Choose a gender!", null, 0, 0, 0, 0);
                }
            }
        });
    }
}