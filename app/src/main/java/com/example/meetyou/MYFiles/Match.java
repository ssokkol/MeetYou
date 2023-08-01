package com.example.meetyou.MYFiles;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.databinding.ActivityMainBinding;
import com.google.firebase.database.*;


public class Match {
    String currentGender;
    @NonNull ActivityMainBinding binding;
    String oppositeGender;
    int minAge;
    int maxAge;
    DatabaseHelper databaseHelper;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
    private int getMaxAge() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("maxAge", 0);
    }
    private void setMinAge(int minAge){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("minAge", minAge);
        editor.apply();
    }
    private void setMaxAge(int maxAge){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("maxAge", maxAge);
        editor.apply();
    }
    public void getMatchedUsers() {
        Query query = usersRef.orderByChild("gender").equalTo(getOppositeGender());


        private String getOppositeGender() {
            return currentUserGender.equals("male") ? "female" : "male";
        }
    }
}
