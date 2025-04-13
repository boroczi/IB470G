package com.example.vizora;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.email = findViewById(R.id.EditTextEmail);
        this.password = findViewById(R.id.EditTextPassword);
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.matches(emailRegex)) {
            Toast.makeText(MainActivity.this, "A megadott email-cím nem megfelelő!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        openOverview();
                    } else {
                        Toast.makeText(MainActivity.this, "Helytelen bejelentkezési adatok!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void openOverview() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("KEY", KEY);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}