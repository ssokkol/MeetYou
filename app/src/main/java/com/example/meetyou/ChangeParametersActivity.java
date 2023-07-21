package com.example.meetyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityChangeParametersBinding;

public class ChangeParametersActivity extends AppCompatActivity {

    ActivityChangeParametersBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get references to Spinners using ViewBinding
        Spinner heightSpinner = binding.heightSpinner;
        Spinner weightSpinner = binding.weightSpinner;
        Spinner smokingSpinner = binding.attitudeToSmokingSpinner;
        Spinner alcoholSpinner = binding.attitudeToAlcoholSpinner;

        // Get string arrays from resources
        String[] heightOptions = getResources().getStringArray(R.array.own_height_options_array);
        String[] weightOptions = getResources().getStringArray(R.array.own_weight_options_array);
        String[] smokingOptions = getResources().getStringArray(R.array.own_attitude_to_smoking_options_array);
        String[] alcoholOptions = getResources().getStringArray(R.array.own_attitude_to_alcohol_options_array);

        // Create adapters for each spinner and associate them with the string arrays
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heightOptions);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightOptions);
        ArrayAdapter<String> smokingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smokingOptions);
        ArrayAdapter<String> alcoholAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alcoholOptions);

        // Set the drop-down view resource for each adapter
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smokingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alcoholAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapters for each spinner
        heightSpinner.setAdapter(heightAdapter);
        weightSpinner.setAdapter(weightAdapter);
        smokingSpinner.setAdapter(smokingAdapter);
        alcoholSpinner.setAdapter(alcoholAdapter);

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeParametersActivity.this, UploadPhotoActivity.class);
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
}