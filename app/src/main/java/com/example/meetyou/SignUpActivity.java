package com.example.meetyou;

// Импортируем необходимые библиотеки

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.databinding.ActivityRegisterationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    public String email;

    // Объявляем переменные и объекты
    ActivityRegisterationBinding binding;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        storageReference = FirebaseStorage.getInstance().getReference();

        getWindow().setStatusBarColor(ContextCompat.getColor(SignUpActivity.this, R.color.main));

        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(v -> finish());

        mAuth = FirebaseAuth.getInstance();

        // Обрабатываем нажатие кнопки "Зарегистрироваться"
        binding.signUpButton.setOnClickListener(v -> {
            email = binding.mailText.getText().toString().trim();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();

            // Проверяем корректность введенного email
            if (isValidEmail(email)) {
                // Проверяем длину пароля
                if (password.length() < 8) {
                    NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.wrong_password_lenght_message), getString(R.string.close), 0, 0, 0,0);
                } else if (!password.matches(".*[A-Z].*")) {
                    NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.wrong_password_chars_format_message), getString(R.string.close), 0, 0, 0,0);
                } else if (password.contains(" ")) {
                    NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.password_has_spaces_message), getString(R.string.close), 0, 0, 0,0);
                } else if (!confirmPassword.equals(password)) {
                    NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.wrong_confirm_password_message), getString(R.string.close), 0, 0, 0,0);
                } else {
                    String sanitizedEmail = sanitizeEmail(email);
                    // Проверяем, зарегистрирован ли уже такой email
                    checkIfEmailIsUsed(email, password);
                    // Создаем нового пользователя и сохраняем его в базу данных
//                    Users users = new Users(sanitizedEmail, false,"none", "none", "none", "none", "none", "none", "none", "none", "#2C59CC", "basic",0);
                    saveUID(sanitizedEmail);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
//                    reference.child(sanitizedEmail).setValue(users);
                }
            } else {
                NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.wrong_mail_format_message), getString(R.string.close), 0, 0, 0,R.color.main);
            }
        });
    }

    @Override
    public void onStart() {
        FirebaseAuth.getInstance().signOut();
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    // Метод для регистрации нового пользователя
    private void registerUser(String email, String password) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.email_was_used_message), getString(R.string.close), 0, 0, 0, R.color.main);
                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, R.string.success_registration_message, Toast.LENGTH_SHORT).show();
                                            usertoDataBase(email, 999);
                                            String sanitizedEmail = sanitizeEmail(email);
                                            createFirebaseStorageFolder(sanitizedEmail);
                                        } else {
                                            NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0, R.color.main);
                                        }
                                    }
                                });
                    }
                } else {
                    Log.e(TAG, "Error checking email existence: " + task.getException());
                    Toast.makeText(SignUpActivity.this, R.string.registration_error_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Метод для создания папки пользователя в Firebase Storage
    private void createFirebaseStorageFolder(String folderName) {
        StorageReference folderRef = storageReference.child(folderName);
        folderRef.putBytes(new byte[0])
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "Папка " + sanitizeEmail(email) + " успешно создана");
                    } else {
                        Log.e(TAG, "Ошибка при создании папки в Firebase Storage: " + task.getException());
                    }
                });
    }

    // Метод для проверки, зарегистрирован ли уже такой email
    private void checkIfEmailIsUsed(String email, String password) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.email_was_used_message), getString(R.string.close), 0, 0, 0, R.color.main);
                    } else {
                        // Если email не зарегистрирован, регистрируем нового пользователя
                        registerUser(email, password);
                        saveUserEmail(email);
                        Intent intent = new Intent(SignUpActivity.this, ChangeGenderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.e(TAG, "Error checking email status: " + task.getException());
                    NotificationHelper.showCustomNotification(SignUpActivity.this, null, getString(R.string.registration_error_message), getString(R.string.close), 0, 0, 0, R.color.main);
                }
            }
        });
    }

    // Метод для сохранения данных пользователя в базу данных
    private void usertoDataBase(String email, int age) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("age", 1);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(email).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Метод для проверки корректности введенного email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Методы для сохранения информации о пользователе в SharedPreferences
    private void saveUserInfo(String email, String password) {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void saveUserID(long userID) {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("userID", userID);
        editor.apply();
    }

    private void saveUserEmail(String email){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail", email);
        editor.apply();
    }

    private void saveUID(String UID){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UID", UID);
        editor.apply();
    }

    // Метод для удаления из email специальных символов, которые не поддерживаются в Firebase
    public String sanitizeEmail(String email) {
        String sanitizedEmail = email.replaceAll("[.@#$\\[\\]]", "");
        return sanitizedEmail;
    }

}
