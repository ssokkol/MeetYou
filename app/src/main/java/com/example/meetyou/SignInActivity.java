package com.example.meetyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    DatabaseHelper databaseHelper;
    private CheckBox rememberMeCheckbox;

    boolean isRememberMeChecked = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHelper = new DatabaseHelper(this);

        mAuth = FirebaseAuth.getInstance();


        getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.main));

        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.mailText.getText().toString().trim();
                String password = binding.password.getText().toString();

                if (!isValidEmail(email)) {
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_mail_format_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(SignInActivity.this, R.string.wrong_mail_format_message, Toast.LENGTH_SHORT).show();
                } else if (email.equals("") || password.equals("")) {
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.zero_input_message), getString(R.string.close), 0, 0, 0,0);
                    //Toast.makeText(SignInActivity.this, R.string.zero_input_message, Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            }
        });

        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);

        if (isRememberMeChecked()) {
            rememberMeCheckbox.setChecked(true);
            loginUser(getSavedEmail(), getSavedPassword());
        }
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this, R.string.success_sign_in_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            if (rememberMeCheckbox.isChecked()) {
                                saveUserCredentials(email, password);
                            }
                        } else{
                            NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_password_or_mail_message), getString(R.string.close), 0, 0, 0,0);
                        }
                    }
                });
    }

    private void saveRememberMeState(boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remember_me", isChecked);
        editor.apply();
    }

    private boolean isRememberMeChecked() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me", false);
    }

    private String getSavedEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    private String getSavedPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }
    private void saveUserCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }
}
