package com.example.meetyou;

import static android.content.ContentValues.TAG;
import static com.example.meetyou.MYFiles.Users.updateUserPhoto;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityUploadPhotoBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ChangeUsersPhotosActivity extends AppCompatActivity {
    int maxFileSizeBytes = 4 * 1024 * 1024;

    private int selectedImageIndex = -1;

    private ImageView selectedImageView;

    private StorageReference storageReference;

    ActivityUploadPhotoBinding binding;
    DatabaseHelper databaseHelper;

    public Boolean firstUploaded = false;
    public Boolean secondUploaded = false;
    public Boolean thirdUploaded = false;
    public Boolean fourthUploaded = false;
    public Boolean fifthUploaded = false;

    private String sPhoto1;
    private String sPhoto2;
    private String sPhoto3;
    private String sPhoto4;
    private String sPhoto5;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    private byte[] photo1;
    private byte[] photo2;
    private byte[] photo3;
    private byte[] photo4;
    private byte[] photo5;

    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        storageReference = FirebaseStorage.getInstance().getReference();

        getWindow().setStatusBarColor(ContextCompat.getColor(ChangeUsersPhotosActivity.this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        imageView5 = findViewById(R.id.image5);

        binding.buttonUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageIndex = 1;
                openGallery();
            }
        });



        binding.buttonUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageIndex = 2;
                openGallery();
            }
        });


        binding.buttonUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageIndex = 3;
                openGallery();
            }
        });


        binding.buttonUpload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageIndex = 4;
                openGallery();
            }
        });


        binding.buttonUpload5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageIndex = 5;
                openGallery();
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstUploaded) {
                    updateUserPhoto(getUID(), "photo1", sPhoto1);
                } else if (secondUploaded) {
                    updateUserPhoto(getUID(), "photo2", sPhoto2);
                } else if (thirdUploaded) {
                    updateUserPhoto(getUID(), "photo3", sPhoto3);
                } else if (fourthUploaded) {
                    updateUserPhoto(getUID(), "photo4", sPhoto4);
                } else if (fifthUploaded) {
                    updateUserPhoto(getUID(), "photo5", sPhoto5);
                }
                finish();
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openGallery() {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setType("image/*");
        startActivityForResult(iGallery, GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null && selectedImageIndex != -1) {
                    ImageView selectedImageView = getSelectedImageView(selectedImageIndex);
                    if (selectedImageView != null) {
                        selectedImageView.setImageURI(selectedImageUri);
                        uploadSelectedPhoto(selectedImageUri, selectedImageIndex);
                    }
                }
            }
        }
        checkAllPhotosUploaded();
    }

    private ImageView getSelectedImageView(int index) {
        switch (index) {
            case 1:
                return imageView1;
            case 2:
                return imageView2;
            case 3:
                return imageView3;
            case 4:
                return imageView4;
            case 5:
                return imageView5;
            default:
                return null;
        }
    }

    private void uploadSelectedPhoto(Uri photoUri, int index) {
        String UID = getUID();

        if (UID == null) {
            Log.e(TAG, "UID is null, cannot upload photo to Firebase Storage.");
            return;
        }

        StorageReference folderRef = storageReference.child(UID);
        String fileExtension = getFileExtension(photoUri);
        String uniqueFileName = "photo" + index + "." + fileExtension;

        StorageReference photoRef = folderRef.child(uniqueFileName);

        photoRef.putFile(photoUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Photo upload success
                    photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String photoUrl = uri.toString();
                        savePhotoUrlToSharedPreferences("photoUrl" + index, photoUrl);
                        updateUserPhoto(UID, "photo" + index, photoUrl);
                        checkAllPhotosUploaded();
                    });
                })
                .addOnFailureListener(exception -> {
                    // Error occurred while uploading the photo
                    Log.e(TAG, "Error uploading photo to Firebase Storage: " + exception.getMessage());
                });
        checkAllPhotosUploaded();
    }


    private void checkAllPhotosUploaded() {
        if (selectedImageIndex != 0) {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeUsersPhotosActivity.this, android.R.color.white));
        } else {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.goNextButton.setTextColor(ContextCompat.getColor(ChangeUsersPhotosActivity.this, R.color.neutral_dark_gray));
        }
    }

    private void uploadPhotoToFirebaseStorage(Uri photoUri, int number) {
        String UID = getUID();

        if (UID == null) {
            Log.e(TAG, "UID is 0, cannot upload photo to Firebase Storage.");
            return;
        }

        String folderName = UID;
        StorageReference folderRef = storageReference.child(folderName);

        String fileExtension = getFileExtension(photoUri);
        String uniqueFileName = "photo" + number + "." + fileExtension;
        if (!fileExtension.equalsIgnoreCase("jpg")) {
            NotificationHelper.showCustomNotification(ChangeUsersPhotosActivity.this, null, "Incorrect file format", null, 0,0,0,0);
        }
        else {
            StorageReference photoRef = folderRef.child(uniqueFileName);

            photoRef.putFile(photoUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Photo upload success
                        photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String photoUrl = uri.toString();
                            // Сохраняем URL (Access token) изображения в соответствующую переменную
                            switch (number) {
                                case 1:
                                    sPhoto1 = photoUrl;
                                    break;
                                case 2:
                                    sPhoto2 = photoUrl;
                                    break;
                                case 3:
                                    sPhoto3 = photoUrl;
                                    break;
                                case 4:
                                    sPhoto4 = photoUrl;
                                    break;
                                case 5:
                                    sPhoto5 = photoUrl;
                                    break;
                            }
                            // Также можно сохранить URL (Access token) изображения в SharedPreferences, если это требуется
                            savePhotoUrlToSharedPreferences("photoUrl" + number, photoUrl);
                        });
                    })
                    .addOnFailureListener(exception -> {
                        // Error occurred while uploading the photo
                        Log.e(TAG, "Error uploading photo to Firebase Storage: " + exception.getMessage());
                    });
        }
    }

    private void savePhotoUrlToSharedPreferences(String key, String photoUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, photoUrl);
        editor.apply();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}