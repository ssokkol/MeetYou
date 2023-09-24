package com.example.meetyou;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.FragmentOwnProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OwnProfileFragment extends Fragment {
    private Context context;
    Toast currentToast;
    public String photo1URL;
    public String photo2URL;
    public String photo3URL;
    public String photo4URL;
    public String photo5URL;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private long lastBackPressedTime = 0;

    FragmentOwnProfileBinding binding;

    public static OwnProfileFragment newInstance(String param1, String param2) {
        OwnProfileFragment fragment = new OwnProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initialize Firebase Messaging token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String token = task.getResult();
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(getUID())
                                .child("userToken")
                                .setValue(token);
                        Log.d("FCM token", token);
                    } else {
                        Log.e("FCM token", "Token registration failed", task.getException());
                    }
                });

        // Load user data
        loadUserData();

        // Location-related setup
        setupLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOwnProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        getStats(new OwnProfileActivity.OnStatsReceivedListener() {
            @Override
            public void onLikessReceived(int likes) {
                binding.likesText.setText(String.valueOf(likes));
            }

            @Override
            public void onMegasympsReceived(int symps) {
                binding.megasText.setText(String.valueOf(symps));
            }

            @Override
            public void onBoostsReceived(int boosts) {
                binding.boostText.setText(String.valueOf(boosts));
            }
        });
        Users.getUserDataFromFirebase(getUID(), new Users.OnUserDataListener() {
            @Override
            public void onDataLoaded(String color, String userName, String bio, String photo1, String photo2, String photo3, String photo4,String photo5, String UID, int age) {

            }

            @Override
            public void onDataLoaded(Users user) {
                photo1URL = user.getPhoto1();
                photo2URL = user.getPhoto2();
                photo3URL = user.getPhoto3();
                photo4URL = user.getPhoto4();
                photo5URL = user.getPhoto5();
            }

            @Override
            public void onDataNotAvailable() {
                // Обработка случая, если данные не удалось загрузить
            }
        });

        try {
            customPhotoLoadingToClient("photo1", binding.profilePhoto);
            customPhotoLoadingToClient("photo1", binding.image1);
            customPhotoLoadingToClient("photo2", binding.image2);
            customPhotoLoadingToClient("photo3", binding.image3);
            customPhotoLoadingToClient("photo4", binding.image4);
            customPhotoLoadingToClient("photo5", binding.image5);
        } catch (Exception e) {
            Log.e("Fragment DEBUG OwnProfile", "An error occurred", e);
        }

        // Проверка наличия разрешения на доступ к местоположению и запрос обновлений местоположения
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        context = null;

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void loadUserData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        photo1URL = user.getPhoto1();
                        photo2URL = user.getPhoto2();
                        photo3URL = user.getPhoto3();
                        photo4URL = user.getPhoto4();
                        photo5URL = user.getPhoto5();
                        // Update UI elements with user data
                        updateUIWithUserData(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle data loading error
            }
        });
    }

    private void updateUIWithUserData(Users user) {
        binding.nameTextView.setText(user.getName() + ", " + user.getAge());
        binding.additionalTextView.setText(user.getBio());
        // Load user photos
        customPhotoLoadingToClient("photo1", binding.profilePhoto);
        customPhotoLoadingToClient("photo1", binding.image1);
        customPhotoLoadingToClient("photo2", binding.image2);
        customPhotoLoadingToClient("photo3", binding.image3);
        customPhotoLoadingToClient("photo4", binding.image4);
        customPhotoLoadingToClient("photo5", binding.image5);
    }

    private void setupLocation() {
        // Get the location manager and set up location listener
        locationManager = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationTextView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Handle status changes
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Handle provider enablement
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Handle provider disablement
            }
        };

        // Check for location permission and request if not granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Request location updates if permission granted
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            }
        }
    }

    private void updateLocationTextView(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (!addresses.isEmpty()) {
                    String country = addresses.get(0).getCountryName();
                    String city = addresses.get(0).getLocality();
                    String locationString = country + ", " + city;
                    binding.locationTextView.setText(locationString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Преобразование закодированного изображения в Bitmap
    private Bitmap getImageBitmapFromSharedPreferences(String key) {
        byte[] imageByteArray = getImageFromSharedPreferences(key);
        if (imageByteArray != null) {
            return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        }
        return null;
    }


    // Получение закодированного изображения из SharedPreferences
    private byte[] getImageFromSharedPreferences(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String base64Image = sharedPreferences.getString(key, null);
        if (base64Image != null) {
            return Base64.decode(base64Image, Base64.DEFAULT);
        }
        return null;
    }

    // Установка флага, указывающего на наличие изменений
    private void setSomethingWasChanged(boolean parameter) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSomethingWasChanged", parameter);
        editor.apply();
    }

    private void customPhotoLoadingToClient(String photoName, ImageView imageView) {
        DatabaseReference Images = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child(photoName);
        Images.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                // Загрузка изображения с помощью библиотеки Picasso
                Picasso.get().load(value).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки, если загрузка изображения не удалась
            }
        });
    }


    private void showImageOverlay(ImageView imageView, RelativeLayout imageOverlay, ImageView expandedImage) {
        expandedImage.setImageDrawable(imageView.getDrawable());
        imageOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOverlay.setVisibility(View.GONE);
            }
        });

        imageOverlay.setVisibility(View.VISIBLE);
        imageOverlay.setAlpha(0f);
        imageOverlay.animate().alpha(1f).setDuration(300).start();
    }

    private void getName(final OwnProfileActivity.OnNameReceivedListener listener) {
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
    public interface OnNameReceivedListener {
        void onNameReceived(String name);
    }

    private void getAge(final OwnProfileActivity.OnAgeReceivedListener listener) {
        DatabaseReference ageRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("age");
        ageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int age = snapshot.getValue(Integer.class);
                if (age != 0) {
                    listener.onAgeReceived(age);
                } else {
                    listener.onAgeReceived(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onAgeReceived(0);
            }
        });
    }
    public interface OnAgeReceivedListener {
        void onAgeReceived(int age);
    }
    private void getBio(final OwnProfileActivity.OnBioReceivedListener listener) {
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child("bio");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String bio = snapshot.getValue(String.class);
                if (bio != null) {
                    listener.onBioReceived(bio);
                } else {
                    listener.onBioReceived(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onBioReceived(" ");
            }
        });
    }

    interface OnBioReceivedListener {
        void onBioReceived(String bio);
    }

    private void getStats(final OwnProfileActivity.OnStatsReceivedListener listener){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(getUID());
        userRef.child("likesCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int likes = snapshot.getValue(Integer.class);
                    listener.onLikessReceived(likes);
                } else{
                    listener.onLikessReceived(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onLikessReceived(0);
            }
        });

        userRef.child("sympsCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int symps = snapshot.getValue(Integer.class);
                    listener.onMegasympsReceived(symps);
                } else{
                    listener.onMegasympsReceived(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onMegasympsReceived(0);
            }
        });

        userRef.child("boostsCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int boosts = snapshot.getValue(Integer.class);
                    listener.onBoostsReceived(boosts);
                } else{
                    listener.onBoostsReceived(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onBoostsReceived(0);
            }
        });
    }


    interface OnStatsReceivedListener{
        void onLikessReceived(int likes);

        void onMegasympsReceived(int symps);

        void onBoostsReceived(int boosts);
    }
    // Метод для получения UID пользователя из SharedPreferences
    private String getUID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}