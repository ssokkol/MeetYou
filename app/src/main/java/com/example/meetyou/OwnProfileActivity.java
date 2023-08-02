package com.example.meetyou;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityOwnProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class OwnProfileActivity extends AppCompatActivity {
    public String photo1URL;
    public String photo2URL;
    public String photo3URL;
    public String photo4URL;
    public String photo5URL;
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Объект для привязки макета активности
    ActivityOwnProfileBinding binding;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Привязка макета активности
        binding = ActivityOwnProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Установка цвета статус-бара
        getWindow().setStatusBarColor(ContextCompat.getColor(OwnProfileActivity.this, R.color.main));

        // Чтение данных из Firebase Database и передача в локальные переменные
        Users.getUserDataFromFirebase(getUID(), new Users.OnUserDataListener() {
            @Override
            public void onDataLoaded(Users user) {
                // Здесь можно использовать значения photo1, photo2, photo3, photo4, photo5
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

        customPhotoLoadingToClient("photo1", binding.profilePhoto);
        customPhotoLoadingToClient("photo1", binding.image1);
        customPhotoLoadingToClient("photo2", binding.image2);
        customPhotoLoadingToClient("photo3", binding.image3);
        customPhotoLoadingToClient("photo4", binding.image4);
        customPhotoLoadingToClient("photo5", binding.image5);

        // Получение данных пользователя и отображение их в представлении
        String nameText = getUserName();
        binding.nameTextView.setText(nameText);
        binding.additionalTextView.setText(getUserBio());

        RelativeLayout imageOverlay = findViewById(R.id.image_overlay);
        ImageView expandedImage = findViewById(R.id.expanded_image);


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


        binding.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.profilePhoto, imageOverlay, expandedImage);
            }
        });


        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.image1, imageOverlay, expandedImage);
            }
        });


        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.image2, imageOverlay, expandedImage);
            }
        });


        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.image3, imageOverlay, expandedImage);
            }
        });


        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.image4, imageOverlay, expandedImage);
            }
        });


        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageOverlay(binding.image5, imageOverlay, expandedImage);
            }
        });
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

//    private int getUserAge() {
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
//        return sharedPreferences.getInt("age", 0);
//    }

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

    private void customPhotoLoadingToClient(String photoName, ImageView imageView){
        DatabaseReference Images = FirebaseDatabase.getInstance().getReference("Users").child(getUID()).child(photoName);
        Images.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Picasso.get().load(value).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}
