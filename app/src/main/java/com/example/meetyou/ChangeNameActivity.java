package com.example.meetyou;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
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
                    NotificationHelper.showCustomNotification(ChangeNameActivity.this, null, getString(R.string.please_enter_new_name_message), getString(R.string.close), 0, 0, 0, 0);
                } else {
                    setSomethingWasChanged(true);
                    Users.updateUserName(getUID(), binding.newNameEditText.getText().toString());
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
        if(binding.newNameEditText.length() > 0){
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
        Users.updateUserName(getUID(), name);
        editor.putString("name", name);
        editor.apply();
    }

    private void setSomethingWasChanged(boolean parameter){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSomethingWasChanged", parameter);
        editor.apply();
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}