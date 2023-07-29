package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.meetyou.databinding.ActivityChangeBioBinding;
import com.example.meetyou.databinding.ActivityCreateBioBinding;

public class ChangeBioActivity extends AppCompatActivity {

    ActivityChangeBioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeBioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getColor(R.color.main));

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int charCount = charSequence.length();
                binding.charsCount.setText(charCount + "/150");
                if(charCount > 150)
                {
                    binding.charsCount.setTextColor(getColor(R.color.red));
                } else if (charCount <= 150)
                {
                    binding.charsCount.setTextColor(getColor(R.color.black));
                }
                checkBioText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkBioText(){
        if(binding.bio.length() > 0){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
        } else{
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }
}