package com.example.meetyou;

import android.Manifest;
import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.meetyou.databinding.ActivityOwnProfileBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OwnProfileActivity extends AppCompatActivity {

    // Переменные для работы с местоположением
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Объект для привязки макета активности
    ActivityOwnProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Привязка макета активности
        binding = ActivityOwnProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Установка цвета статус-бара
        getWindow().setStatusBarColor(ContextCompat.getColor(OwnProfileActivity.this, R.color.main));

        // Получение данных пользователя и отображение их в представлении
        String nameText = getUserName() + ", " + Integer.toString(getUserAge());
        binding.nameTextView.setText(nameText);
        binding.additionalTextView.setText(getUserBio());

        // Загрузка изображений из SharedPreferences и отображение их в ImageView
        binding.profilePhoto.setImageBitmap(getImageBitmapFromSharedPreferences("photo1"));
        binding.image1.setImageBitmap(getImageBitmapFromSharedPreferences("photo1"));
        binding.image2.setImageBitmap(getImageBitmapFromSharedPreferences("photo2"));
        binding.image3.setImageBitmap(getImageBitmapFromSharedPreferences("photo3"));
        binding.image4.setImageBitmap(getImageBitmapFromSharedPreferences("photo4"));
        binding.image5.setImageBitmap(getImageBitmapFromSharedPreferences("photo5"));

        // Обработчик кнопки "Настройки", переход к активности OptionsActivity
        binding.settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnProfileActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        // Обработчик кнопки "Найти", переход к активности MainActivity и завершение текущей активности
        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Получение LocationManager и установка слушателя для обновления местоположения
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationTextView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Метод вызывается при изменении статуса провайдера (GPS или сеть)
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Метод вызывается, когда пользователь включает провайдер (GPS или сеть)
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Метод вызывается, когда пользователь отключает провайдер (GPS или сеть)
            }
        };

        // Проверка наличия разрешения на доступ к местоположению, и запрос разрешения, если требуется
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Проверка, были ли внесены изменения, и пересоздание активности для их отображения
        if (getChanges()) {
            setSomethingWasChanged(false);
            recreate();
        }

        // Проверка наличия разрешения на доступ к местоположению и запрос обновлений местоположения
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Остановка обновлений местоположения при остановке активности
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    // Обновление текстового представления с местоположением
    private void updateLocationTextView(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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

    // Методы для получения данных пользователя из SharedPreferences
    private String getUserBio() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("bio", "");
    }

    private int getUserAge() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("age", 0);
    }

    private String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    private boolean getChanges() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isSomethingWasChanged", false);
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
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String base64Image = sharedPreferences.getString(key, null);
        if (base64Image != null) {
            return Base64.decode(base64Image, Base64.DEFAULT);
        }
        return null;
    }

    // Установка флага, указывающего на наличие изменений
    private void setSomethingWasChanged(boolean parameter) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSomethingWasChanged", parameter);
        editor.apply();
    }
}
