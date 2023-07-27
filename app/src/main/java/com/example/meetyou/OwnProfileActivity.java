package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;

import com.example.meetyou.databinding.ActivityOwnProfileBinding;

public class OwnProfileActivity extends AppCompatActivity {

    ActivityOwnProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setStatusBarColor(ContextCompat.getColor(OwnProfileActivity.this, R.color.main));
    }


}