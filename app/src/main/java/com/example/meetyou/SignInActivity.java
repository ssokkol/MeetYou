package com.example.meetyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.meetyou.Database.DatabaseHelper;
import com.example.meetyou.MYFiles.NotificationHelper;
import com.example.meetyou.MYFiles.Users;
import com.example.meetyou.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    // Подключение привязки к макету активности
    ActivitySignInBinding binding;

    // Переменная для работы с базой данных
    DatabaseHelper databaseHelper;

    // Флаг для запоминания пользователя (опциональный выбор)
    private CheckBox rememberMeCheckbox;

    // Флаг, отмечающий состояние "Запомнить меня"
    boolean isRememberMeChecked = false;

    // Объект для аутентификации через Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Изменение цвета статус-бара
        getWindow().setStatusBarColor(ContextCompat.getColor(SignInActivity.this, R.color.main));


        // Получение экземпляра Firebase для аутентификации
        mAuth = FirebaseAuth.getInstance();

        // Инициализация помощника для работы с базой данных
        databaseHelper = new DatabaseHelper(this);

        // Обработчик кнопки "Назад" - закрывает активность
        AppCompatButton backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Обработчик кнопки "Войти" - выполняет попытку входа
        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.mailText.getText().toString().trim();
                String password = binding.password.getText().toString().trim();

                // Проверка корректности формата электронной почты
                if (!isValidEmail(email)) {
                    // Вывод уведомления об ошибке в формате почты
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_mail_format_message), getString(R.string.close), 0, 0, 0, 0);
                } else if (email.isEmpty() || password.isEmpty()) {
                    // Вывод уведомления об ошибке ввода данных (пустые поля)
                    NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.zero_input_message), getString(R.string.close), 0, 0, 0, 0);
                } else {
                    // Вызов метода для попытки входа пользователя
                    loginUser(binding.mailText.getText().toString(), binding.password.getText().toString().trim());
                }
            }
        });
    }

    // Проверка корректности формата электронной почты
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Метод для входа пользователя
    private void loginUser(String email, String password) {
        // Если вход успешен, получаем уникальный идентификатор пользователя (UID) и загружаем его данные
        String userUID = sanitizeEmail(email);
        saveUID(userUID);
        saveUserEmail(binding.mailText.getText().toString().trim());
        saveUserPassword(binding.password.getText().toString().trim());
        saveCheckboxState(binding.rememberMeCheckbox.isChecked());
        binding.registrationWindow.setVisibility(View.GONE);
        binding.logoImageView.startAnimation(AnimationUtils.loadAnimation(SignInActivity.this, R.anim.rotate_logo));
        // Вызов метода Firebase для входа с указанными данными
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Вызов метода для загрузки данных пользователя
                            Users.getUserDataFromFirebase(userUID, new Users.OnUserDataListener() {
                                @Override
                                public void onDataLoaded(String color, String userName, String bio,String photo1, String photo2, String photo3, String photo4,String photo5, String UID) {
                                }

                                @Override
                                public void onDataLoaded(Users user) {
                                    // Сохранение данных пользователя в SharedPreferences и запуск основной активности
                                    saveUserCredentials(user);
                                    startMainActivity();
                                }

                                @Override
                                public void onDataNotAvailable() {
                                    // Вывод уведомления об ошибке загрузки данных пользователя
                                    Toast.makeText(SignInActivity.this, R.string.data_not_available_message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // Вывод уведомления об ошибке - неправильный пароль или почта
                            NotificationHelper.showCustomNotification(SignInActivity.this, null, getString(R.string.wrong_password_or_mail_message), getString(R.string.close), 0, 0, 0, 0);
                        }
                    }
                });
    }

    // Метод для сохранения учетных данных пользователя в SharedPreferences
    private void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    private void saveUserPassword(String password){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

    // Метод для сохранения данных пользователя в SharedPreferences
    private void saveUserCredentials(Users user) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", binding.mailText.getText().toString().trim());
        editor.putString("name", user.getName());
        editor.putString("bio", user.getBio());
        editor.putString("height", user.getHeight());
        editor.putString("width", user.getWeight());
        editor.putString("gender", user.getGender());
        editor.putString("findGender", user.getFindGender());
        editor.putString("hobbies", user.getHobbies());
        editor.putString("target", user.getTarget());
        editor.putInt("age", user.getAge());
        editor.apply();
    }

    // Запуск основной активности приложения
    private void startMainActivity() {
        Intent intent = new Intent(SignInActivity.this, OwnProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void saveCheckboxState(boolean isChecked){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remember_me", isChecked);
        editor.apply();
    }

    // Метод для "санитарной" обработки email (удаление символов [@, .])
    public String sanitizeEmail(String email) {
        String sanitizedEmail = email.replaceAll("[.@#$\\[\\]]", "");
        return sanitizedEmail;
    }

    // Метод для сохранения UID пользователя в SharedPreferences
    private void saveUID(String UID) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UID", UID);
        editor.apply();
    }
}