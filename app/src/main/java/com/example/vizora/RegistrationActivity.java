package com.example.vizora;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private EditText passwordAgain;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("KEY", 0) != KEY) {
            finish();
        }

        this.fullName = findViewById(R.id.EditTextFullName);
        this.email = findViewById(R.id.EditTextEmail);
        this.password = findViewById(R.id.EditTextPassword);
        this.passwordAgain = findViewById(R.id.EditTextPasswordAgain);
        this.mAuth = FirebaseAuth.getInstance();
        this.notificationHandler = new NotificationHandler(this);
    }

    public void register(View view) {
        String fullName = this.fullName.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String passwordAgain = this.passwordAgain.getText().toString();
        String fullNameRegex = "^[A-ZÁÉÍÓÖŐÚÜŰ][a-záéíóöőúüű]+(\\s[A-ZÁÉÍÓÖŐÚÜŰ][a-záéíóöőúüű]+)+$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!fullName.matches(fullNameRegex)) {
            Toast.makeText(RegistrationActivity.this, "A teljes név formátuma nem megfelelő!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(RegistrationActivity.this, "A két megadott jelszó nem egyezik meg!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(RegistrationActivity.this, "A jelszónak legalább 8 karakter hosszúnak kell lennie!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.matches(emailRegex)) {
            Toast.makeText(RegistrationActivity.this, "A megadott email-cím nem megfelelő!", Toast.LENGTH_SHORT).show();
            return;
        }

        this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        notificationHandler.send("Sikeres regisztráció! Jelentkezz be, hogy hozzáadd első mérődórád!");
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        finish();
    }

    public void cancel(View view) {
        finish();
    }
}