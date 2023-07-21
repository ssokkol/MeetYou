package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

public class StartAcivity extends AppCompatActivity {

    Spinner spinner;
    public static final String[] languages = {"Select Language", "Russian", "English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acivity);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(0);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedLang = parent.getItemAtPosition(position).toString();
//
//                if(selectedLang.equals("Russian"))
//                {
//                    setLocalise(StartAcivity.this, "ru");
//                    finish();
//                    startActivity(getIntent());
//                }
//                else if(selectedLang.equals("English"))
//                {
//                    setLocalise(StartAcivity.this, "en");
//                    finish();
//                    startActivity(getIntent());
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        getWindow().setStatusBarColor(ContextCompat.getColor(StartAcivity.this, R.color.main));

        AppCompatButton signUpButton = findViewById(R.id.sign_up_button);
        AppCompatButton signInButton = findViewById(R.id.sign_in_button);

        if (isUserLoggedIn()) {
            startActivity(new Intent(StartAcivity.this, MainActivity.class));
        } else {
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartAcivity.this, RegisterationActivity.class);
                    startActivity(intent);
                }
            });

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StartAcivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

//    public void setLocalise(Activity activity, String langCode){
//        Locale locale = new Locale(langCode);
//        locale.setDefault(locale);
//        Resources resources = activity.getResources();
//        Configuration config = resources.getConfiguration();
//        config.setLocale(locale);
//        resources.updateConfiguration(config, resources.getDisplayMetrics());
//    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");
        String userPassword = sharedPreferences.getString("password", "");

        // Возможно, вам также потребуется дополнительная проверка на валидность пользовательских данных
        return !userEmail.isEmpty() && !userPassword.isEmpty();
    }
}
