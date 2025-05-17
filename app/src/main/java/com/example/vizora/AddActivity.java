package com.example.vizora;

import android.os.Build;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference meters;
    private EditText address;
    private EditText latestValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("KEY", 0) != KEY) {
            finish();
        }

        this.address = findViewById(R.id.EditTextAddress);
        this.latestValue = findViewById(R.id.EditTextLatestValue);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
        }

        this.firestore = FirebaseFirestore.getInstance();
        this.meters = firestore.collection("items");
    }

    public void add(View view) {
        String address = this.address.getText().toString();
        String latestValue = this.latestValue.getText().toString();

        if (address.isEmpty() || latestValue.isEmpty()) {
            Toast.makeText(AddActivity.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return;
        }

        java.time.LocalDate lastDayOfMonthLocalDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lastDayOfMonthLocalDate = YearMonth.now().atEndOfMonth();
        }

        Date lastDayOfMonthDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lastDayOfMonthDate = Date.from(lastDayOfMonthLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        this.meters.add(new Meter(user.getEmail(), address, Float.parseFloat(latestValue), new Date(), lastDayOfMonthDate))
                .addOnSuccessListener(
                        documentReference -> {
                            Toast.makeText(AddActivity.this, "Sikeres hozzáadás!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                );
    }

    public void cancel(View view) {
        finish();
    }
}