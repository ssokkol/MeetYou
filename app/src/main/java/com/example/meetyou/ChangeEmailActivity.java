package com.example.meetyou;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meetyou.databinding.ActivityChangeEmailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    ActivityChangeEmailBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setStatusBarColor(getColor(R.color.main));

        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        binding.currentMailEditText.addTextChangedListener(new TextWatcher() {
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

        binding.newMailEditText.addTextChangedListener(new TextWatcher() {
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

        binding.confirmMailEditText.addTextChangedListener(new TextWatcher() {
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
                changeEmail(binding.newMailEditText.getText().toString().trim());
            }
        });

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkEditText(){
        if(binding.currentMailEditText.length() > 0 && binding.newMailEditText.length() > 0 && binding.confirmMailEditText.length() > 0){
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
        } else{
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }


    private void changeEmail(String newEmail) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String currentEmail = binding.currentMailEditText.getText().toString().trim();

            // Swap the parameters for EmailAuthProvider.getCredential()
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, newEmail);

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(newEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChangeEmailActivity.this, "Email updated successfully.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(ChangeEmailActivity.this, "Failed to update email. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(ChangeEmailActivity.this, "Incorrect current email.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangeEmailActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
}
