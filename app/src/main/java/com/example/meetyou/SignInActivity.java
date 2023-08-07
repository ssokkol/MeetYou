package com.example.meetyou;

import static com.example.meetyou.MYFiles.Users.saveUserDataToSharedPreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

        // Получение состояния флага "Запомнить меня" из SharedPreferences
        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox);
        isRememberMeChecked = isRememberMeChecked(); // Получаем значение из SharedPreferences
        rememberMeCheckbox.setChecked(isRememberMeChecked); // Устанавливаем состояние RememberMeCheckbox
        if (isRememberMeChecked) {
            String savedEmail = getSavedEmail();
            String savedPassword = getSavedPassword();
            if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
                loginUser(savedEmail, savedPassword);
            } else {
                clearUserCredentials();
                Toast.makeText(this, "Saved email or password is empty. Please log in again.", Toast.LENGTH_SHORT).show();
            }
        }

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

                // Сохранение состояния флага "Запомнить меня" в SharedPreferences
                saveRememberMeState(binding.rememberMeCheckbox.isChecked());

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
        // Вызов метода Firebase для входа с указанными данными
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (binding.rememberMeCheckbox.isChecked()) {
                                saveUserCredentials(email, password);
                            } else {
                                clearUserCredentials();
                            }

                            startMainActivity();
                            // Если вход успешен, получаем уникальный идентификатор пользователя (UID) и загружаем его данные
                            String userUID = sanitizeEmail(email);
                            saveUID(userUID);
                            saveUserDataToSharedPreferences(SignInActivity.this, userUID);

                            // Вызов метода для загрузки данных пользователя
                            Users.getUserDataFromFirebase(userUID, new Users.OnUserDataListener() {
                                @Override
                                public void onDataLoaded(String color, String userName, String bio,String photo1, String photo2, String photo3, String photo4,String photo5, String UID) {
                                    // Здесь вы можете обработать полученные данные, если это необходимо.
                                    // Например, отобразить их на экране или выполнить другие действия с этими данными.
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

    // Метод для очистки учетных данных пользователя
    private void clearUserCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

    // Метод для сохранения учетных данных пользователя в SharedPreferences
    private void saveUserCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    // Метод для сохранения данных пользователя в SharedPreferences
    private void saveUserCredentials(Users user) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", user.getEmail());
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

    // Сохранение состояния флага "Запомнить меня" в SharedPreferences
    private void saveRememberMeState(boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remember_me", isChecked);
        editor.apply();
    }

    // Получение состояния флага "Запомнить меня" из SharedPreferences
    private boolean isRememberMeChecked() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me", false);
    }

    // Получение сохраненного email из SharedPreferences
    private String getSavedEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    // Получение сохраненного password из SharedPreferences
    private String getSavedPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
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