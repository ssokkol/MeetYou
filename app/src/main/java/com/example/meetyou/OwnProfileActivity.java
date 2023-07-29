package com.example.meetyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
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
import android.view.View;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityOwnProfileBinding;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OwnProfileActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    ActivityOwnProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(OwnProfileActivity.this, R.color.main));

        binding.nameTextView.setText(getUserName() + ", " + getUserAge());

        binding.additionalTextView.setText(getUserBio());

        binding.profilePhoto.setImageBitmap(getImageBitmapFromSharedPreferences("photo1"));
        binding.image1.setImageBitmap(getImageBitmapFromSharedPreferences("photo1"));
        binding.image2.setImageBitmap(getImageBitmapFromSharedPreferences("photo2"));
        binding.image3.setImageBitmap(getImageBitmapFromSharedPreferences("photo3"));
        binding.image4.setImageBitmap(getImageBitmapFromSharedPreferences("photo4"));
        binding.image5.setImageBitmap(getImageBitmapFromSharedPreferences("photo5"));

        binding.settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnProfileActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationTextView(location);
            }

        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getChanges()){
            setSomethingWasChanged(false);
            recreate();
        }
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void updateLocationTextView(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (!addresses.isEmpty()) {
                    String country = addresses.get(0).getCountryName().toString();
                    String city = addresses.get(0).getLocality().toString();
                    String locationString = country + ", " + city;
                    binding.locationTextView.setText(locationString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getUserBio(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("bio", "");
    }

    private String getUserAge(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("age", "");
    }

    private String getUserName(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    private boolean getChanges(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isSomethingWasChanged", false);
    }

    private Bitmap getImageBitmapFromSharedPreferences(String key) {
        byte[] imageByteArray = getImageFromSharedPreferences(key);
        if (imageByteArray != null) {
            return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        }
        return null;
    }

    private byte[] getImageFromSharedPreferences(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String base64Image = sharedPreferences.getString(key, null);
        if (base64Image != null) {
            return Base64.decode(base64Image, Base64.DEFAULT);
        }
        return null;
    }

    private void setSomethingWasChanged(boolean parameter){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSomethingWasChanged", parameter);
        editor.apply();
    }
}
