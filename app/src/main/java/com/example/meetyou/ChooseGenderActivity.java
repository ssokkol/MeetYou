package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityChooseGenderBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class ChooseGenderActivity extends AppCompatActivity {

    private ActivityChooseGenderBinding binding;
    private DatabaseHelper databaseHelper;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private boolean isFemale = false;
    private boolean isMale = false;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseGenderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));
        Users users = new Users(getUID(), "none","none", "none", "none", "none", "none", "none","none", "none", "none", "none", "#2C59CC", "basic", "none", "none", "none", "none", 0, 0, 0, 0, false);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");
        reference.child(getUID()).setValue(users);
        databaseHelper = new DatabaseHelper(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gender");

        binding.femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFemale) {
                    isFemale = true;
                    isMale = false;

                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.white));
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.white));

                } else {
                    isFemale = false;
                    resetGenderButtons();
                }
            }
        });

        binding.maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMale) {
                    isMale = true;
                    isFemale = false;
                    binding.maleButton.setBackgroundResource(R.drawable.btn_bg_blue);
                    binding.maleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.white));
                    binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
                    binding.femaleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.black));
                    binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
                    binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.white));
                } else {
                    isMale = false;
                    resetGenderButtons();
                }
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMale && !isFemale) {
                    NotificationHelper.showCustomNotification(ChooseGenderActivity.this, null, getString(R.string.choose_your_gender), getString(R.string.close), 0, 0, 0,0);
                } else if (isFemale) {
                    Users.updateUserGender(getUID(), "female");
                    Users.updateUserColor(getUID(), "#E337FF");
                    Users.updateUserStatus(getUID(), "basic");
                    Users.updateUID(getUID());
                    Intent intent = new Intent(ChooseGenderActivity.this, CreateBioActivity.class);
                    startActivity(intent);
                } else if (isMale) {
                    Users.updateUserGender(getUID(), "male");
                    Users.updateUserColor(getUID(), "#374BFF");
                    Users.updateUserStatus(getUID(), "basic");
                    Users.updateUID(getUID());
                    Intent intent = new Intent(ChooseGenderActivity.this, CreateBioActivity.class);
                    startActivity(intent);
                }
                else {
                    NotificationHelper.showCustomNotification(ChooseGenderActivity.this, null, getString(R.string.registration_error_message), null, 0,0,0,0);
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
        }
    }

    private void resetGenderButtons() {
        binding.femaleButton.setBackgroundResource(R.drawable.btn_bg_gray);
        binding.femaleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.black));
        binding.maleButton.setBackgroundResource(R.drawable.btn_bg_gray);
        binding.maleButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, android.R.color.black));
        binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
        binding.goNextButton.setTextColor(ContextCompat.getColor(ChooseGenderActivity.this, R.color.neutral_dark_gray));
        isFemale = false;
        isMale = false;
    }

    @IgnoreExtraProperties
    public class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }

    public void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private String getUserEmail(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    private void updateGenderInDatabase(String userEmail, String gender) {
        mDatabase.child("users").child(userEmail).child("gender").setValue(gender)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(ChooseGenderActivity.this, CreateBioActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        NotificationHelper.showCustomNotification(ChooseGenderActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0, 0);
                        Toast.makeText(ChooseGenderActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}