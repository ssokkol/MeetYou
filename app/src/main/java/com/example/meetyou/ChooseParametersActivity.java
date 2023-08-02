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
import com.example.meetyou.databinding.ActivityChooseParametersBinding;

public class ChooseParametersActivity extends AppCompatActivity {

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
                String selectedHeight = heightSpinner.getSelectedItem().toString();
                String selectedWeight = weightSpinner.getSelectedItem().toString();
                Users.updateUserHeight(getUID(), selectedHeight);
                Users.updateUserWeight(getUID(), selectedWeight);

//                long result = databaseHelper.insertParameters(getUserID(), selectedHeight, selectedWeight);
//                if(result != -1){
                Intent intent = new Intent(ChooseParametersActivity.this, UploadPhotoActivity.class);
                startActivity(intent);
//                }else{
//                    NotificationHelper.showCustomNotification(ChangeParametersActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0,0);
////                    Toast.makeText(ChangeParametersActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
//                }
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