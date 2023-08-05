package com.example.meetyou;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivityUploadPhotoBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class UploadPhotoActivity extends AppCompatActivity {

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

        getWindow().setStatusBarColor(ContextCompat.getColor(UploadPhotoActivity.this, R.color.main));

        databaseHelper = new DatabaseHelper(this);

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        imageView4 = findViewById(R.id.image4);
        imageView5 = findViewById(R.id.image5);

        binding.buttonUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageView = imageView1;
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });



        binding.buttonUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageView = imageView2;
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });


        binding.buttonUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageView = imageView3;
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });


        binding.buttonUpload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageView = imageView4;
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });


        binding.buttonUpload5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageView = imageView5;
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        binding.goNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstUploaded && secondUploaded && thirdUploaded && fourthUploaded && fifthUploaded) {
                    Users.updateUserPhoto(getUID(), "photo1", sPhoto1);
                    Users.updateUserPhoto(getUID(), "photo2", sPhoto2);
                    Users.updateUserPhoto(getUID(), "photo3", sPhoto3);
                    Users.updateUserPhoto(getUID(), "photo4", sPhoto4);
                    Users.updateUserPhoto(getUID(), "photo5", sPhoto5);
                    Intent intent = new Intent(UploadPhotoActivity.this, ChooseInterestsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UploadPhotoActivity.this, R.string.upload_all_photos_message, Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                if (selectedImageView != null) {
                    selectedImageView.setImageURI(data.getData());

                    if (selectedImageView == imageView1) {
                        photo1 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo1", photo1);
                        uploadPhotoToFirebaseStorage(data.getData(), 1);
                        firstUploaded = true;
                    } else if (selectedImageView == imageView2) {
                        photo2 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo2", photo2);
                        uploadPhotoToFirebaseStorage(data.getData(), 2);
                        secondUploaded = true;
                    } else if (selectedImageView == imageView3) {
                        photo3 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo3", photo3);
                        uploadPhotoToFirebaseStorage(data.getData(), 3);
                        thirdUploaded = true;
                    } else if (selectedImageView == imageView4) {
                        photo4 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo4", photo4);
                        uploadPhotoToFirebaseStorage(data.getData(), 4);
                        fourthUploaded = true;
                    } else if (selectedImageView == imageView5) {
                        photo5 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo5", photo5);
                        uploadPhotoToFirebaseStorage(data.getData(), 5);
                        fifthUploaded = true;
                    }
                    applyRoundedCorners(selectedImageView);
                    checkAllPhotosUploaded();
                }
            }
        }
    }


    private void applyRoundedCorners(ImageView imageView) {
        Drawable originalDrawable = imageView.getDrawable();
        if (originalDrawable == null) return;

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        int cornerRadius = 150;
        Bitmap roundedBitmap = getRoundedCornerBitmap(originalBitmap, cornerRadius);

        imageView.setImageBitmap(roundedBitmap);
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadius) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(output);

        final int color = 0xff424242;
        final android.graphics.Paint paint = new android.graphics.Paint();
        final android.graphics.Rect rect = new android.graphics.Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final android.graphics.RectF rectF = new android.graphics.RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void checkAllPhotosUploaded() {
        if (firstUploaded && secondUploaded && thirdUploaded && fourthUploaded && fifthUploaded) {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.goNextButton.setTextColor(ContextCompat.getColor(UploadPhotoActivity.this, android.R.color.white));
        } else {
            binding.goNextButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.goNextButton.setTextColor(ContextCompat.getColor(UploadPhotoActivity.this, R.color.neutral_dark_gray));
        }
    }


    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void saveImageToSharedPreferences(String key, byte[] imageData) {
        String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, base64Image);
        editor.apply();
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