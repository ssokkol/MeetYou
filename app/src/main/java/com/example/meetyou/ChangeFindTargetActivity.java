package com.example.meetyou;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityChangeFindTargetBinding;

public class ChangeFindTargetActivity extends AppCompatActivity {

    ActivityChangeFindTargetBinding binding;

    private boolean isShortChanged = false;
    private boolean isLongChanged = false;
    private boolean isFunChanged = false;
    private boolean isSureChanged = false;
    private boolean isSexChanged = false;
    private boolean isFriendsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFindTargetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        binding.shortTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = true;
                isLongChanged = false;
                isFunChanged = false;
                isSureChanged = false;
                isSexChanged = false;
                isFriendsChanged = false;

                updateButtonState();
            }
        });

        binding.longTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = false;
                isLongChanged = true;
                isFunChanged = false;
                isSureChanged = false;
                isSexChanged = false;
                isFriendsChanged = false;

                updateButtonState();
            }
        });

        binding.funTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = false;
                isLongChanged = false;
                isFunChanged = true;
                isSureChanged = false;
                isSexChanged = false;
                isFriendsChanged = false;

                updateButtonState();
            }
        });

        binding.notSureTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = false;
                isLongChanged = false;
                isFunChanged = false;
                isSureChanged = true;
                isSexChanged = false;
                isFriendsChanged = false;

                updateButtonState();
            }
        });

        binding.quickSexTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = false;
                isLongChanged = false;
                isFunChanged = false;
                isSureChanged = false;
                isSexChanged = true;
                isFriendsChanged = false;

                updateButtonState();
            }
        });

        binding.friendsTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShortChanged = false;
                isLongChanged = false;
                isFunChanged = false;
                isSureChanged = false;
                isSexChanged = false;
                isFriendsChanged = true;

                updateButtonState();
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFriendsChanged || isShortChanged || isFunChanged || isLongChanged || isSexChanged || isSureChanged){
                    Intent intent = new Intent(ChangeFindTargetActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    NotificationHelper.showCustomNotification(ChangeFindTargetActivity.this, null, getString(R.string.pick_a_few_targets_message), getString(R.string.close), 0, 0, 0,0);
                }
            }
        });
    }

    private void updateButtonState() {
        binding.shortTermImage.setImageResource(isShortChanged ? R.drawable.short_term_selected : R.drawable.short_term);
        binding.longTermImage.setImageResource(isLongChanged ? R.drawable.long_term_selected : R.drawable.long_term);
        binding.funTermImage.setImageResource(isFunChanged ? R.drawable.fun_selected : R.drawable.fun);
        binding.notSureTermImage.setImageResource(isSureChanged ? R.drawable.not_sure_selected : R.drawable.not_sure);
        binding.quickSexTermImage.setImageResource(isSexChanged ? R.drawable.quick_sex_selected : R.drawable.quick_sex);
        binding.friendsTermImage.setImageResource(isFriendsChanged ? R.drawable.friends_selected : R.drawable.friends);
        if(isFriendsChanged || isShortChanged || isFunChanged || isLongChanged || isSexChanged || isSureChanged){
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(getColor(R.color.white));
        } else {
            binding.goNextButton.setTextColor(getColor(R.color.neutral_dark_gray));
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
        }
    }

}