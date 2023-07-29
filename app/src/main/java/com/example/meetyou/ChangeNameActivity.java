package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityChangeNameBinding;

public class ChangeNameActivity extends AppCompatActivity {


    ActivityChangeNameBinding binding;

    public boolean isNextActivated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getColor(R.color.main));

        binding.currentNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.newNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNextActivated) {
                    NotificationHelper.showCustomNotification(ChangeNameActivity.this, null, "Please enter NewName or CurrentName", getString(R.string.close), 0, 0, 0, 0);
                } else if (!binding.currentNameEditText.getText().toString().equals(getUserName())) {
                    NotificationHelper.showCustomNotification(ChangeNameActivity.this, null, "Wrong name", getString(R.string.close), 0, 0, 0, 0);
                } else {
                    setSomethingWasChanged(true);
                    saveUserName(binding.newNameEditText.getText().toString());
                    finish();
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

    private void checkEditText(){
        if(binding.currentNameEditText.length() > 0 && binding.newNameEditText.length() > 0){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
            isNextActivated = true;
        } else{
            isNextActivated = false;
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }

    private void saveUserName(String name){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    private String getUserName(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    private void setSomethingWasChanged(boolean parameter){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSomethingWasChanged", parameter);
        editor.apply();
    }
}