package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityCreateBioBinding;

import java.util.Calendar;

public class CreateBioActivity extends AppCompatActivity {

    ActivityCreateBioBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCreateBioBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding.bio.setTextColor(Color.BLACK);
        binding.enterName.setTextColor(Color.BLACK);
        binding.enterBirthYear.setTextColor(Color.BLACK);
        binding.enterBirthMonth.setTextColor(Color.BLACK);
        binding.enterBirthDay.setTextColor(Color.BLACK);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(ContextCompat.getColor(CreateBioActivity.this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        binding.enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAllText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.enterBirthYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAllText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.enterBirthMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAllText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.enterBirthDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAllText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ничего не делаем перед изменением текста
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Счетчик букав
                int charCount = charSequence.length();
                binding.charsCount.setText(charCount + "/150");
                if(charCount > 150)
                {
                    binding.charsCount.setTextColor(getResources().getColor(R.color.red));
                } else if (charCount <= 150)
                {
                    binding.charsCount.setTextColor(getResources().getColor(R.color.black));
                }
                checkAllText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Ничего не делаем после изменения текста
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Херь всякая про типа
                String bio = binding.bio.getText().toString();
                String name = binding.enterName.getText().toString();
                // Вычисление возраста
                String birthYearStr = binding.enterBirthYear.getText().toString();
                String birthMonthStr = binding.enterBirthMonth.getText().toString();
                String birthDayStr = binding.enterBirthDay.getText().toString();

                // Ограничение на имя (не более 30 символов)
                if (name.length() > 30) {
                    NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.name_chars_count_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(CreateBioActivity.this, R.string.name_chars_count_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!name.matches("[a-zA-Zа-яА-Я\\s]+")) {
                    NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.name_chars_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(CreateBioActivity.this, R.string.name_chars_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Ограничение на био (не более 150 символов)
                else if (bio.length() > 150) {
                    NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.bio_chars_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(CreateBioActivity.this, R.string.bio_chars_message, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!birthYearStr.isEmpty() && !birthMonthStr.isEmpty() && !birthDayStr.isEmpty()) {
                    int birthYear = Integer.parseInt(birthYearStr);
                    int birthMonth = Integer.parseInt(birthMonthStr);
                    int birthDay = Integer.parseInt(birthDayStr);

                    if (calculateAge(birthYear, birthMonth, birthDay) > 72) {
                        Toast.makeText(CreateBioActivity.this, R.string.old_ages_message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Проверка, что пользователь не младше 18 лет
                    if (isUnderage(birthYear, birthMonth, birthDay)) {
                        NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.eighteen_years_message), getString(R.string.close), 0, 0, 0,0);
                        //Toast.makeText(CreateBioActivity.this, R.string.eighteen_years_message, Toast.LENGTH_SHORT).show();
                    } else if (birthMonth <= 0 || birthMonth > 12)
                    {
                        NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.wrong_date_message), getString(R.string.close), 0, 0, 0,0);
//                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    } else if (birthDay <= 0 || birthDay > 31) {
                        NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.wrong_date_message), getString(R.string.close), 0, 0, 0,0);
//                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    } else if(birthMonth == 2 && birthDay > 29){
                        NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.wrong_date_message), getString(R.string.close), 0, 0, 0,0);
//                        Toast.makeText(CreateBioActivity.this, R.string.wrong_date_message, Toast.LENGTH_SHORT).show();
                    }
                    else if(binding.bio.length() != 0){
                            saveUserBio(bio);
                            saveUserName(name);
                            saveUserAge(calculateAge(Integer.parseInt(birthYearStr), Integer.parseInt(birthMonthStr), Integer.parseInt(birthDayStr)));
                            Toast.makeText(CreateBioActivity.this, R.string.data_saved_successfully_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateBioActivity.this, ChooseParametersActivity.class);
                            startActivity(intent);
//                            NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.error_data_saving_message), getString(R.string.close), 0, 0, 0,0);
                            //Toast.makeText(CreateBioActivity.this, R.string.error_data_saving_message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    NotificationHelper.showCustomNotification(CreateBioActivity.this, null, getString(R.string.enter_date_in_full_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(CreateBioActivity.this, R.string.enter_date_in_full_message, Toast.LENGTH_SHORT).show();
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

    private boolean isUnderage(int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month - 1, day);

        if (birthDate.after(currentDate)) {
            return true;
        }

        currentDate.add(Calendar.YEAR, -18);
        return (birthDate.after(currentDate));
    }

    private long getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getLong("userID", -1);
    }

    private int calculateAge(int birthYear, int birthMonth, int birthDay) {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }
        saveAges(18, age);
        return age;
    }

    private void checkAllText(){
        if(binding.bio.length() > 0 && binding.enterName.length() > 0 && binding.enterBirthDay.length() > 0 && binding.enterBirthMonth.length() > 0 && binding.enterBirthYear.length() > 0)
        {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(getColor(R.color.white));
        }
        else
        {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.goNextButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }

    private void saveUserBio(String bio){
        Users.updateUserBio(getUID(), bio);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bio", bio);
        editor.apply();
    }

    private void saveUserName(String name){
        Users.updateUserName(getUID(), name);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    private void saveUserAge(int age){
        Users.updateUserAge(getUID(), age);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("age", String.valueOf(age));
        editor.apply();
    }

    private void saveAges(float minAge, float maxAge){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("minAge", minAge);
        editor.putFloat("maxAge", maxAge);
        editor.apply();
    }


    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
