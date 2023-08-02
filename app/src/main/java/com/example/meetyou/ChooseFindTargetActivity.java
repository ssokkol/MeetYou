package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChooseFindTargetBinding;

public class ChooseFindTargetActivity extends AppCompatActivity {

    ActivityChooseFindTargetBinding binding;

    private boolean isShortChanged = false;
    private boolean isLongChanged = false;
    private boolean isFunChanged = false;
    private boolean isSureChanged = false;
    private boolean isSexChanged = false;
    private boolean isFriendsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseFindTargetBinding.inflate(getLayoutInflater());
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

                binding.shortTermImage.setImageResource(R.drawable.short_term_selected);
                binding.longTermImage.setImageResource(R.drawable.long_term);
                binding.funTermImage.setImageResource(R.drawable.fun);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex);
                binding.friendsTermImage.setImageResource(R.drawable.friends);

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

                binding.shortTermImage.setImageResource(R.drawable.short_term);
                binding.longTermImage.setImageResource(R.drawable.long_term_selected);
                binding.funTermImage.setImageResource(R.drawable.fun);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex);
                binding.friendsTermImage.setImageResource(R.drawable.friends);

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

                binding.shortTermImage.setImageResource(R.drawable.short_term);
                binding.longTermImage.setImageResource(R.drawable.long_term);
                binding.funTermImage.setImageResource(R.drawable.fun_selected);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex);
                binding.friendsTermImage.setImageResource(R.drawable.friends);

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

                binding.shortTermImage.setImageResource(R.drawable.short_term);
                binding.longTermImage.setImageResource(R.drawable.long_term);
                binding.funTermImage.setImageResource(R.drawable.fun);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure_selected);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex);
                binding.friendsTermImage.setImageResource(R.drawable.friends);

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

                binding.shortTermImage.setImageResource(R.drawable.short_term);
                binding.longTermImage.setImageResource(R.drawable.long_term);
                binding.funTermImage.setImageResource(R.drawable.fun);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex_selected);
                binding.friendsTermImage.setImageResource(R.drawable.friends);

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

                binding.shortTermImage.setImageResource(R.drawable.short_term);
                binding.longTermImage.setImageResource(R.drawable.long_term);
                binding.funTermImage.setImageResource(R.drawable.fun);
                binding.notSureTermImage.setImageResource(R.drawable.not_sure);
                binding.quickSexTermImage.setImageResource(R.drawable.quick_sex);
                binding.friendsTermImage.setImageResource(R.drawable.friends_selected);

                updateButtonState();
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFriendsChanged || isShortChanged || isFunChanged || isLongChanged || isSexChanged || isSureChanged){
                    if(isFriendsChanged)
                    {
                        Users.updateUserTarget(getUID(), "friends");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    } else if (isShortChanged)
                    {
                        Users.updateUserTarget(getUID(), "short-term");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    } else if (isLongChanged)
                    {
                        Users.updateUserTarget(getUID(), "long-term");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    } else if (isFunChanged)
                    {
                        Users.updateUserTarget(getUID(), "fun");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    } else if (isSexChanged)
                    {
                        Users.updateUserTarget(getUID(), "sex");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    } else if (isSureChanged)
                    {
                        Users.updateUserTarget(getUID(), "not-sure");
                        Intent intent = new Intent(ChooseFindTargetActivity.this, ChoosePartnersParametersActivity.class);
                        startActivity(intent);
                    }
                }else{
                    NotificationHelper.showCustomNotification(ChooseFindTargetActivity.this, null, getString(R.string.pick_a_few_targets_message), getString(R.string.close), 0, 0, 0,0);
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

    private void updateButtonState() {
//        binding.shortTermImage.setImageResource(isShortChanged ? R.drawable.short_term_selected : R.drawable.short_term);
//        binding.longTermImage.setImageResource(isLongChanged ? R.drawable.long_term_selected : R.drawable.long_term);
//        binding.funTermImage.setImageResource(isFunChanged ? R.drawable.fun_selected : R.drawable.fun);
//        binding.notSureTermImage.setImageResource(isSureChanged ? R.drawable.not_sure_selected : R.drawable.not_sure);
//        binding.quickSexTermImage.setImageResource(isSexChanged ? R.drawable.quick_sex_selected : R.drawable.quick_sex);
//        binding.friendsTermImage.setImageResource(isFriendsChanged ? R.drawable.friends_selected : R.drawable.friends);
        if(isFriendsChanged || isShortChanged || isFunChanged || isLongChanged || isSexChanged || isSureChanged){
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(getColor(R.color.white));
        } else {
            binding.goNextButton.setTextColor(getColor(R.color.neutral_dark_gray));
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
        }
    }

    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}