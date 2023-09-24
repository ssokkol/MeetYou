package com.example.meetyou;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.PhotoAdapter;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityMainBinding;
import com.example.meetyou.databinding.FragmentMessangerBinding;
import com.example.meetyou.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Context context;

    FragmentSearchBinding binding;

    private GestureDetector gestureDetector;
    private List<String> currentUrls = new ArrayList<>();
    private List<String> viewedUsersList = new ArrayList<>();
    FirebaseAuth mAuth;
    String foundUID;

    int[] photos = {
            R.drawable.photo_1,
            R.drawable.photo_2,
            R.drawable.photo_3,
            R.drawable.photo_4,
            R.drawable.photo_5
    };
    private int currentPosition = 0;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findUser();
        binding = FragmentSearchBinding.bind(view);

        binding.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUser();
                getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                    @Override
                    public void onProfilePhotoReceived(String profilePhoto) {

                        Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                        NotificationHelper.showHeart(context);
                    }
                });
            }
        });

        binding.megasbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getStatus(new MainActivity.OnStatusReceivedListener() {
                    @Override
                    public void OnStatusReceived(String userSub) {
                        if(!userSub.equals("luxe")){
                            getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                                @Override
                                public void onProfilePhotoReceived(String profilePhoto) {
                                    Users.updateUserMegasympsLuxe(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                                }
                            });
                        }
                        else{
                            getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                                @Override
                                public void onProfilePhotoReceived(String profilePhoto) {
                                    Users.updateUserMegasymps(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                                }
                            });
                        }
                    }
                });


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
                getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                    @Override
                    public void onProfilePhotoReceived(String profilePhoto) {

                        Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                        NotificationHelper.showHeart(context);
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
                        getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                            @Override
                            public void onProfilePhotoReceived(String profilePhoto) {

                                Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                                NotificationHelper.showHeart(context);
                            }
                        });
                    } else {
                        findUser();
                    }
                } else {
                    if (deltaX < 0) {
                        findUser();
                    } else {

                        getProfilePhoto(new MainActivity.OnProfilePhotoReceivedListener() {
                            @Override
                            public void onProfilePhotoReceived(String profilePhoto) {

                                Users.updateUserLikes(profilePhoto, currentUrls.get(0),getUID(), foundUID, context);
                                NotificationHelper.showHeart(context);
                            }
                        });
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
                public void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4, String photo5, String UID, int age) {
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
                    NotificationHelper.showCustomNotification(context, null, getString(R.string.something_went_wrong_message), null, 0,0,0,0);
                }
            });
        }
    }
    }

    private String getUID(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
    private void getFindGender(final MainActivity.OnFindGenderReceivedListener listener) {
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

    private void getName(final MainActivity.OnNameReceivedListener listener) {
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
        getFindWeight(new MainActivity.onFindWeightReceivedListener() {
            @Override
            public void onFindWeightReceived(String findWeight) {
                getFindHeight(new MainActivity.onFindHeightReceivedListener() {
                    @Override
                    public void onFindHeightReceived(String findHeight) {
                        getGender(new MainActivity.OnGenderReceivedListener() {
                            @Override
                            public void onGenderReceived(String gender) {
                                getFindGender(new MainActivity.OnFindGenderReceivedListener() {
                                    @Override
                                    public void onFindGenderReceived(String userFindGender) {
                                        Log.w("findGender", userFindGender);
                                        Users.getRandomUserFromPool(context ,getMinAge(), getMaxAge(), gender, userFindGender, findHeight, findWeight, getUID(), new Users.OnUserDataListener() {
                                            @Override
                                            public void onDataLoaded(String color, String userName, String userBio, String photo1, String photo2, String photo3, String photo4,String photo5, String UID, int age) {
                                                binding.informationTextView.setText(userBio);
                                                binding.nameTextView.setText(userName + ", " + String.valueOf(age));
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
                        });
                    }
                });
            }
        });
    }

    private void getProfilePhoto(final MainActivity.OnProfilePhotoReceivedListener listener) {
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

    private void getGender(final MainActivity.OnGenderReceivedListener listener){
        DatabaseReference genderRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("gender");
        genderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gender = snapshot.getValue(String.class);
                if(gender != null){
                    listener.onGenderReceived(gender);
                }else{
                    listener.onGenderReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onGenderReceived(" ");
            }
        });
    }

    interface OnGenderReceivedListener{
        void onGenderReceived(String gender);
    }

    private void getFindWeight(final MainActivity.onFindWeightReceivedListener listener){
        DatabaseReference findWeightRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("findWeight");
        findWeightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String findWeight = snapshot.getValue(String.class);
                if(findWeight != null){
                    listener.onFindWeightReceived(findWeight);
                } else{
                    listener.onFindWeightReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFindWeightReceived(" ");
            }
        });
    }

    interface onFindWeightReceivedListener{
        void onFindWeightReceived(String findWeight);
    }

    private void getFindHeight(final MainActivity.onFindHeightReceivedListener listener){
        DatabaseReference findHeightRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("findHeight");
        findHeightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String findHeight = snapshot.getValue(String.class);
                if(findHeight != null){
                    listener.onFindHeightReceived(findHeight);
                } else{
                    listener.onFindHeightReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFindHeightReceived(" ");
            }
        });
    }

    interface onFindHeightReceivedListener{
        void onFindHeightReceived(String findHeight);
    }
    public static void getUserByUID(String uid, final Users.OnUserDataListener listener) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                if (user != null) {
                    listener.onDataLoaded(user.getColor(), user.getName(), user.getBio(),
                            user.getPhoto1(), user.getPhoto2(), user.getPhoto3(), user.getPhoto4(), user.getPhoto5(), user.getUID(), user.getAge());
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


    private void getStatus(final MainActivity.OnStatusReceivedListener listener){
        DatabaseReference statusRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("status");
        statusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userSub = snapshot.getValue(String.class);
                if (snapshot.exists()) {
                    listener.OnStatusReceived(userSub);
                } else {
                    listener.OnStatusReceived("basic");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnStatusReceived("basic");
            }
        });
    }


    public interface OnStatusReceivedListener{
        void OnStatusReceived(String userSub);
    }

    private float getMinAge(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("minAge", 18);
    }
    private float getMaxAge(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("maxAge", 32);
    }
}