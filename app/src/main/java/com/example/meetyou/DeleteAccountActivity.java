package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetyou.databinding.ActivityDeleteAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class DeleteAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    ActivityDeleteAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        getWindow().setStatusBarColor(getColor(R.color.main));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference folderReference = storage.getReference().child("/"+getUID());
        DatabaseReference userRef = usersRef.child(getUID());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding.confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPassword = binding.confirmPasswordEditText.getText().toString().trim();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if (firebaseUser != null) {
                    List<? extends UserInfo> providerData = firebaseUser.getProviderData();

                    for (UserInfo userInfo : providerData) {
                        if (userInfo.getProviderId().equals("password")) {
                            String userPassword = userInfo.getUid();

                            if (userPassword.equals(enteredPassword)) {
                                userRef.removeValue();
                                folderReference.delete();
                                clearUserCredentials();
                                currentUser.delete();
                                Intent intent = new Intent(DeleteAccountActivity.this, StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {

                            }
                            break;
                        }
                    }
                }
            }
        });
    }

    private void checkEditText(){
        if(binding.confirmPasswordEditText.length() >= 7){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
        } else{
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }

    private void clearUserCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.remove("remember_me");
        editor.apply();
    }

    private String getUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("UID", "");
    }
}