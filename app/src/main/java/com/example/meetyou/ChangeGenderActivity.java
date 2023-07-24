package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityChangeGenderBinding;

public class ChangeGenderActivity extends AppCompatActivity {

    private ActivityChangeGenderBinding binding;
    private DatabaseHelper databaseHelper;

    private boolean isFemale = false;
    private boolean isMale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        databaseHelper = new DatabaseHelper(this);


        binding.femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFemale) {
                    isFemale = true;
                    isMale = false;

                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                } else {
                    isFemale = false;
                    resetGenderButtons();
                }
            }
        });

        binding.maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMale) {
                    isMale = true;
                    isFemale = false;
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.white));
                } else {
                    isMale = false;
                    resetGenderButtons();
                }
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMale && !isFemale) {
                    NotificationHelper.showCustomNotification(ChangeGenderActivity.this, null, getString(R.string.change_your_gender), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(ChangeGenderActivity.this, "Change your gender", Toast.LENGTH_SHORT).show();
                } else if (isFemale) {
                    long result = databaseHelper.insertGender(getUserID(), "female");
                    if (result != -1) {
                        Intent intent = new Intent(ChangeGenderActivity.this, CreateBioActivity.class);
                        startActivity(intent);
                    } else {
                        NotificationHelper.showCustomNotification(ChangeGenderActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0,0);
                        //Toast.makeText(ChangeGenderActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
                    }
                } else if (isMale) {
                    long result = databaseHelper.insertGender(getUserID(), "male");
                    if (result != -1) {
                        Intent intent = new Intent(ChangeGenderActivity.this, CreateBioActivity.class);
                        startActivity(intent);
                    } else {
                        NotificationHelper.showCustomNotification(ChangeGenderActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0,0);
                        //Toast.makeText(ChangeGenderActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
                    }
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
    private void resetGenderButtons() {
        binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
        binding.femaleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
        binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
        binding.maleButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, android.R.color.black));
        binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
        binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeGenderActivity.this, R.color.neutral_dark_gray));
        isFemale = false;
        isMale = false;
    }

    private long getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }
}
