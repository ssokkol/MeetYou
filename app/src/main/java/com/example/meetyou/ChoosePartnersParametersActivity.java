package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChoosePartnersParametersBinding;

public class ChoosePartnersParametersActivity extends AppCompatActivity {

    ActivityChoosePartnersParametersBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosePartnersParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(ChoosePartnersParametersActivity.this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        Spinner heightSpinner = binding.heightSpinner;
        Spinner weightSpinner = binding.weightSpinner;
        Spinner smokingSpinner = binding.attitudeToSmokingSpinner;
        Spinner alcoholSpinner = binding.attitudeToAlcoholSpinner;

        String[] heightOptions = getResources().getStringArray(R.array.own_height_options_array);
        String[] weightOptions = getResources().getStringArray(R.array.own_weight_options_array);
        String[] smokingOptions = getResources().getStringArray(R.array.own_attitude_to_smoking_options_array);
        String[] alcoholOptions = getResources().getStringArray(R.array.own_attitude_to_alcohol_options_array);

        // Create adapters for each spinner and associate them with the string arrays
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heightOptions);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightOptions);
        ArrayAdapter<String> smokingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smokingOptions);
        ArrayAdapter<String> alcoholAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alcoholOptions);

        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smokingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcoholAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heightSpinner.setAdapter(heightAdapter);
        weightSpinner.setAdapter(weightAdapter);
        smokingSpinner.setAdapter(smokingAdapter);
        alcoholSpinner.setAdapter(alcoholAdapter);

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedHeightIndex = binding.heightSpinner.getSelectedItemPosition();
                int selectedWeightIndex = binding.weightSpinner.getSelectedItemPosition();
                int selectedSmokingIndex = binding.attitudeToSmokingSpinner.getSelectedItemPosition();
                int selectedAlcoholIndex = binding.attitudeToAlcoholSpinner.getSelectedItemPosition();
                switch (selectedHeightIndex){
                    case 0:
                        Users.updateUserFindHeight(getUID(), "Under 160");
                        break;
                    case 1:
                        Users.updateUserFindHeight(getUID(), "160-170");
                        break;
                    case 2:
                        Users.updateUserFindHeight(getUID(), "170-180");
                        break;
                    case 3:
                        Users.updateUserFindHeight(getUID(), "170-180");
                        break;
                    default:
                        Users.updateUserFindHeight(getUID(), "Under 160");
                        break;
                }
                switch (selectedWeightIndex){
                    case 0:
                        Users.updateUserFindWeight(getUID(), "Under 50 kg");
                        break;
                    case 1:
                        Users.updateUserFindWeight(getUID(), "50-65 kg");
                        break;
                    case 2:
                        Users.updateUserFindWeight(getUID(), "65-80 kg");
                        break;
                    case 3:
                        Users.updateUserFindWeight(getUID(), "Over 80 kg");
                        break;
                    default:
                        Users.updateUserFindWeight(getUID(), "Under 50 kg");
                        break;
                }

                Intent intent = new Intent(ChoosePartnersParametersActivity.this, OwnProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }

}