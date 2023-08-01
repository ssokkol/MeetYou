package com.example.meetyou;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meetyou.databinding.ActivityChangeUsersPhotosBinding;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class ChangeUsersPhotosActivity extends AppCompatActivity {

    private boolean isSomethingChanged = false;
    private boolean isNextActivated = false;

    private ImageView selectedImageView;
    private StorageReference storageReference;
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
    ActivityChangeUsersPhotosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeUsersPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getColor(R.color.main));

        binding.image1.setImageBitmap(getImageBitmapFromSharedPreferences("photo1"));
        binding.image2.setImageBitmap(getImageBitmapFromSharedPreferences("photo2"));
        binding.image3.setImageBitmap(getImageBitmapFromSharedPreferences("photo3"));
        binding.image4.setImageBitmap(getImageBitmapFromSharedPreferences("photo4"));
        binding.image5.setImageBitmap(getImageBitmapFromSharedPreferences("photo5"));

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

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNextActivated){
                    finish();
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
                        isSomethingChanged = true;
                    } else if (selectedImageView == imageView2) {
                        photo2 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo2", photo2);
                        uploadPhotoToFirebaseStorage(data.getData(), 2);
                        isSomethingChanged = true;
                    } else if (selectedImageView == imageView3) {
                        photo3 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo3", photo3);
                        uploadPhotoToFirebaseStorage(data.getData(), 3);
                        isSomethingChanged = true;
                    } else if (selectedImageView == imageView4) {
                        photo4 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo4", photo4);
                        uploadPhotoToFirebaseStorage(data.getData(), 4);
                        isSomethingChanged = true;
                    } else if (selectedImageView == imageView5) {
                        photo5 = getByteArrayFromBitmap(((BitmapDrawable) selectedImageView.getDrawable()).getBitmap());
                        saveImageToSharedPreferences("photo5", photo5);
                        uploadPhotoToFirebaseStorage(data.getData(), 5);
                        isSomethingChanged = true;
                    }
                    applyRoundedCorners(selectedImageView);
                    checkIsSomethingChanged();
                }
            }
        }
    }

    private void checkIsSomethingChanged() {
        if(isSomethingChanged){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
            isNextActivated = true;
        } else{
            isNextActivated = false;
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
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

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
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
                    // Photo upload success, do something if needed
                })
                .addOnFailureListener(exception -> {
                    // Error occurred while uploading the photo
                    Log.e(TAG, "Error uploading photo to Firebase Storage: " + exception.getMessage());
                });
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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