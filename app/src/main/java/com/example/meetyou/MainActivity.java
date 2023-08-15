package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.PhotoAdapter;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.Messenger.MessengerActivity;
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
    private GestureDetector gestureDetector;
    private List<String> currentUrls = new ArrayList<>();
    private List<String> viewedUsersList = new ArrayList<>();
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
        findWeight = getFindWeight();
        findHeight = getFindHeight();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUser();
                getProfilePhoto(new OnProfilePhotoReceivedListener() {
                    @Override
                    public void onProfilePhotoReceived(String profilePhoto) {

                        Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, MainActivity.this);
                        NotificationHelper.showHeart(MainActivity.this);
                    }
                });
            }
        });

        binding.megasbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUser();
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
        binding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
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

        binding.gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackUserList();
            }
        });

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
           @Override
           public boolean onDoubleTap(MotionEvent event){
               findUser();
               getProfilePhoto(new OnProfilePhotoReceivedListener() {
                   @Override
                   public void onProfilePhotoReceived(String profilePhoto) {

                       Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, MainActivity.this);
                       NotificationHelper.showHeart(MainActivity.this);
                   }
               });
               return true;
           }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float deltaX = e2.getX() - e1.getX();
                float deltaY = e2.getY() - e1.getY();

                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    if (deltaY < 0 && deltaX > 0) {
                        findUser();
                        getProfilePhoto(new OnProfilePhotoReceivedListener() {
                            @Override
                            public void onProfilePhotoReceived(String profilePhoto) {

                                Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, MainActivity.this);
                                NotificationHelper.showHeart(MainActivity.this);
                            }
                        });
                    } else {
                        findUser();
                    }
                } else {
                    if (deltaX < 0) {
                        // Свайп влево
                    } else {
                        // Свайп вправо
                    }
                }

                return true;
            }
        });
    }

    private void goBackUserList() {if (!viewedUsersList.isEmpty()) {
        viewedUsersList.remove(viewedUsersList.size() - 1);

        if (!viewedUsersList.isEmpty()) {
            String lastViewedUID = viewedUsersList.get(viewedUsersList.size() - 1);
            getUserByUID(lastViewedUID, new Users.OnUserDataListener() {
                @Override
                public void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4, String photo5, String UID) {
                    binding.informationTextView.setText(userBio);
                    binding.nameTextView.setText(userName);
                    binding.genderColor2View.setBackgroundColor(Color.parseColor(color));
                    binding.genderColorView.setBackgroundColor(Color.parseColor(color));

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
                    NotificationHelper.showCustomNotification(MainActivity.this, null, getString(R.string.something_went_wrong_message), null, 0,0,0,0);
                }
            });
        }
    }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
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
    private void getFindGender(final OnFindGenderReceivedListener listener) {
        DatabaseReference findGenderRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("findGender");
        findGenderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userFindGender = snapshot.getValue(String.class);
                if (userFindGender != null) {
                    listener.onFindGenderReceived(userFindGender);
                } else {
                    listener.onFindGenderReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFindGenderReceived(" ");
            }
        });
    }
    interface OnFindGenderReceivedListener {
        void onFindGenderReceived(String userFindGender);
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
        getFindGender(new OnFindGenderReceivedListener() {
            @Override
            public void onFindGenderReceived(String userFindGender) {
                Users.getRandomUserFromPool(getMinAge(), getMaxAge(), gender, userFindGender, findHeight, findWeight, getUID(), new Users.OnUserDataListener() {
                    @Override
                    public void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4,String photo5, String UID) {
                        binding.informationTextView.setText(userBio);
                        binding.nameTextView.setText(userName);
                        binding.genderColor2View.setBackgroundColor(Color.parseColor(color));
                        binding.genderColorView.setBackgroundColor(Color.parseColor(color));
                        foundUID = UID;
                        viewedUsersList.add(UID);
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
        });

    }

    private void getProfilePhoto(final OnProfilePhotoReceivedListener listener) {
        DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("photo1");
        photoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profilePhoto = snapshot.getValue(String.class);
                if (profilePhoto != null) {
                    listener.onProfilePhotoReceived(profilePhoto);
                } else {
                    listener.onProfilePhotoReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onProfilePhotoReceived(" ");
            }
        });
    }
    interface OnProfilePhotoReceivedListener {
        void onProfilePhotoReceived(String profilePhoto);
    }
    public static void getUserByUID(String uid, final Users.OnUserDataListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                if (user != null) {
                    listener.onDataLoaded(user.getColor(), user.getName(), user.getBio(),
                            user.getPhoto1(), user.getPhoto2(), user.getPhoto3(), user.getPhoto4(), user.getPhoto5(), user.getUID());
                } else {
                    listener.onDataNotAvailable();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onDataNotAvailable();
            }
        });
    }


    private float getMinAge(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("minAge", 18);
    }
    private float getMaxAge(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("maxAge", 32);
    }
}