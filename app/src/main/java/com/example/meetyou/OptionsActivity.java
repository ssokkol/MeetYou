package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.Messenger.MessengerActivity;
import com.example.meetyou.databinding.ActivityOptionsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OptionsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(OptionsActivity.this, R.color.main));


        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, MessengerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeNameActivity.class);
                startActivity(intent);
            }
        });

        binding.changeBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeBioActivity.class);
                startActivity(intent);
            }
        });

        binding.changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });

        binding.changeInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeInterestActivity.class);
                startActivity(intent);
            }
        });

        binding.profileColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeUserColorActivity.class);
                startActivity(intent);
            }
        });

        binding.updatePhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangeUsersPhotosActivity.class);
                startActivity(intent);
            }
        });

        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationHelper.showCustomNotificationWithTwoButtons(OptionsActivity.this, null, "Are you sure?", "cancel", "sign out", 0, 0, 0, 0, new NotificationHelper.ButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        clearUserCredentials();
                        mAuth.signOut();
                        Intent intent = new Intent(OptionsActivity.this, StartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        binding.deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.ageSlider.setValues(getMinAge(), getMaxAge());
    }

    @Override
    public void onStop() {
        super.onStop();

        List<Float> sliderValues = binding.ageSlider.getValues();
        float minAge = sliderValues.get(0);
        float maxAge = sliderValues.get(1);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("minAge", minAge);
        editor.putFloat("maxAge", maxAge);
        editor.apply();
    }

    private float getMinAge(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("minAge", 18);
    }
    private float getMaxAge(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("maxAge", 32);
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }

    private void clearUserCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.remove("remember_me");
        editor.apply();
    }
}