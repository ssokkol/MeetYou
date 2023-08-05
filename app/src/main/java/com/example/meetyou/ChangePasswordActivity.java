package com.example.meetyou;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.meetyou.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;

    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button confirmButton, goBackButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        currentPasswordEditText = findViewById(R.id.current_password_edit_text);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        confirmButton = findViewById(R.id.confirm_button);
        goBackButton = findViewById(R.id.go_back_button);

        getWindow().setStatusBarColor(ContextCompat.getColor(ChangePasswordActivity.this, R.color.main));

        binding.currentPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        binding.newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        binding.confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(binding.newPasswordEditText.getText().toString().trim());
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkEditText() {
        if (binding.currentPasswordEditText.length() > 0 && binding.newPasswordEditText.length() > 0 && binding.confirmPasswordEditText.length() > 0) {
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_blue);
            binding.confirmButton.setTextColor(getColor(R.color.white));
        } else {
            binding.confirmButton.setBackgroundResource(R.drawable.button_background_gray);
            binding.confirmButton.setTextColor(getColor(R.color.neutral_dark_gray));
        }
    }

    private void changePassword(String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String currentPassword = binding.currentPasswordEditText.getText().toString().trim();

            AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChangePasswordActivity.this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(ChangePasswordActivity.this, "Failed to update password. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(ChangePasswordActivity.this, "Incorrect current password.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
}
