package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public String photo1URL;
    public String photo2URL;
    public String photo3URL;
    public String photo4URL;
    public String photo5URL;

    FirebaseAuth mAuth;
    @NonNull ActivityMainBinding binding;
    DatabaseHelper databaseHelper;
    private String[] photoURLs; // Remove initialization here
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHelper = new DatabaseHelper(this);

        String userEmail = getUserEmail();
        String userPassword = getUserPassword();

        binding.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationHelper.showHeart(MainActivity.this);
            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (currentPosition == 0) {
                    binding.chooseImage.setImageResource(R.drawable.left2choosed);
                } else if (currentPosition == 1) {
                    binding.chooseImage.setImageResource(R.drawable.left1choosed);
                } else if (currentPosition == 2) {
                    binding.chooseImage.setImageResource(R.drawable.centerchoosed);
                } else if (currentPosition == 3) {
                    binding.chooseImage.setImageResource(R.drawable.right1choosed);
                } else {
                    binding.chooseImage.setImageResource(R.drawable.right2choosed);
                }
            }
        });

        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OwnProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        binding.dislikebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users.getRandomUserFromPool(getUID(), new Users.OnUserDataListener() {
                    @Override
                    public void onDataLoaded(String userName, String userBio, String photo1, String photo2, String photo3, String photo4, String photo5) {
                        binding.nameTextView.setText(userName);
                        binding.informationTextView.setText(userBio);
                        photo1URL = photo1;
                        photo2URL = photo2;
                        photo3URL = photo3;
                        photo4URL = photo4;
                        photo5URL = photo5;

                        photoURLs = new String[]{photo1URL, photo2URL, photo3URL, photo4URL, photo5URL};

                        PhotoAdapter photoAdapter = new PhotoAdapter(MainActivity.this, photoURLs);
                        binding.viewPager.setAdapter(photoAdapter);
                    }

                    @Override
                    public void onDataLoaded(String userName) {

                    }

                    @Override
                    public void onDataLoaded(Users user) {

                    }

                    @Override
                    public void onDataNotAvailable() {
                        // Handle the case where no suitable user is found
                    }
                });
            }
        });

    }

    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    private String getUserPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }

    private String getUID(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}