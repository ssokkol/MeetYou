package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChooseParametersBinding;

public class ChooseParametersActivity extends AppCompatActivity {

    private int selectedHeightIndex = 0;
    private int selectedWeightIndex = 0;
    private int selectedSmokingIndex = 0;
    private int selectedAlcoholIndex = 0;

    private String[] heightOptions;
    private String[] weightOptions;
    private String[] smokingOptions;
    private String[] alcoholOptions;

    ActivityChooseParametersBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        Spinner heightSpinner = binding.heightSpinner;
        Spinner weightSpinner = binding.weightSpinner;
        Spinner smokingSpinner = binding.attitudeToSmokingSpinner;
        Spinner alcoholSpinner = binding.attitudeToAlcoholSpinner;

        heightOptions = getResources().getStringArray(R.array.own_height_options_array);
        weightOptions = getResources().getStringArray(R.array.own_weight_options_array);
        smokingOptions = getResources().getStringArray(R.array.own_attitude_to_smoking_options_array);
        alcoholOptions = getResources().getStringArray(R.array.own_attitude_to_alcohol_options_array);

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

        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHeightIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWeightIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        smokingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSmokingIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alcoholSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAlcoholIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedHeightIndex = binding.heightSpinner.getSelectedItemPosition();
                int selectedWeightIndex = binding.weightSpinner.getSelectedItemPosition();
                int selectedSmokingIndex = binding.attitudeToSmokingSpinner.getSelectedItemPosition();
                int selectedAlcoholIndex = binding.attitudeToAlcoholSpinner.getSelectedItemPosition();
                switch (selectedHeightIndex){
                    case 0:
                        Users.updateUserHeight(getUID(), "Under 160");
                        break;
                    case 1:
                        Users.updateUserHeight(getUID(), "160-170");
                        break;
                    case 2:
                        Users.updateUserHeight(getUID(), "170-180");
                        break;
                    case 3:
                        Users.updateUserHeight(getUID(), "170-180");
                        break;
                    default:
                        Users.updateUserHeight(getUID(), "Under 160");
                        break;
                }
                switch (selectedWeightIndex){
                    case 0:
                        Users.updateUserWeight(getUID(), "Under 50 kg");
                        break;
                    case 1:
                        Users.updateUserWeight(getUID(), "50-65 kg");
                        break;
                    case 2:
                        Users.updateUserWeight(getUID(), "65-80 kg");
                        break;
                    case 3:
                        Users.updateUserWeight(getUID(), "Over 80 kg");
                        break;
                    default:
                        Users.updateUserWeight(getUID(), "Under 50 kg");
                        break;
                }
                Intent intent = new Intent(ChooseParametersActivity.this, UploadPhotoActivity.class);
                startActivity(intent);
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