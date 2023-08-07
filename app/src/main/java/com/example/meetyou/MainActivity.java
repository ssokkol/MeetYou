package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.PhotoAdapter;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> currentUrls = new ArrayList<>();
    FirebaseAuth mAuth;
    @NonNull ActivityMainBinding binding;
    String gender;
    String findGender;
    String findWeight;
    String findHeight;
    Users user = new Users();
    String foundUID;

    int[] photos = {
            R.drawable.photo_1,
            R.drawable.photo_2,
            R.drawable.photo_3,
            R.drawable.photo_4,
            R.drawable.photo_5
    };
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.main));
        gender = getGender();
        findGender = getFindGender();
        findWeight = getFindWeight();
        findHeight = getFindHeight();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        binding.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users.updateUserLikes(getUID(), foundUID);
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
                findUser();
            }
        });

    }
    @Override
    public void onStart() {

        super.onStart();
        findUser();
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
    private String getGender(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("gender", "");
//        return user.getGender();
    }
    private String getFindGender(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("findGender", "");
//        return user.getFindGender();
    }
    private String getFindWeight(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("findWeight", "");
//        return user.getFindWeight();
    }
    private String getFindHeight(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("findHeight", "");
//        return user.getFindHeight();
    }

    private void getName(final OnNameReceivedListener listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("name");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    listener.onNameReceived(name);
                } else {
                    listener.onNameReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onNameReceived(" ");
            }
        });
    }
    interface OnNameReceivedListener {
        void onNameReceived(String name);
    }

    private void findUser(){
//        for(int i = 0; i < 10; i++){
//            Log.e("Error", getFindHeight());
//            getName(new OnNameReceivedListener() {
//                @Override
//                public void onNameReceived(String name) {
//                    Log.e("Error", name);
//                }
//            });
//        }
        Users.getRandomUserFromPool(gender,findGender,findHeight,findWeight, new Users.OnUserDataListener() {
            @Override
            public void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4,String photo5, String UID) {
                binding.informationTextView.setText(userBio);
                binding.nameTextView.setText(userName);
                binding.genderColor2View.setBackgroundColor(Color.parseColor(color));
                binding.genderColorView.setBackgroundColor(Color.parseColor(color));
                foundUID = UID;
                for(int i = 0; i < 10; i++){
                    Log.e("Error", foundUID+" Hello ");
                }
                currentUrls.clear();
                currentUrls.add(photo1);
                currentUrls.add(photo2);
                currentUrls.add(photo3);
                currentUrls.add(photo4);
                currentUrls.add(photo5);
                PhotoAdapter photoAdapter = new PhotoAdapter(currentUrls);
                binding.viewPager.setAdapter(photoAdapter);
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataLoaded(Users user) {

            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }
}