package com.example.vizora;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ModifyActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference meters;
    private EditText address;
    private String documentId;
    private TextView latestValue;
    private TextView latestDate;
    private TextView deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("KEY", 0) != KEY) {
            finish();
        }

        if (getIntent().getStringExtra("ID") == null) {
            finish();
        }

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
        }

        this.firestore = FirebaseFirestore.getInstance();
        this.meters = firestore.collection("items");

        this.documentId = getIntent().getStringExtra("ID");
        this.address = findViewById(R.id.EditTextAddress);
        this.latestValue = findViewById(R.id.TextViewLatestValue);
        this.latestDate = findViewById(R.id.TextViewLatestDate);
        this.deadline = findViewById(R.id.TextViewDeadline);

        meters.document(documentId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                address.setText(documentSnapshot.getString("address"));
                latestValue.setText(String.valueOf(documentSnapshot.getDouble("latestValue")));
                latestDate.setText(Objects.requireNonNull(documentSnapshot.getDate("latestDate")).toString());
                deadline.setText(Objects.requireNonNull(documentSnapshot.getDate("deadline")).toString());
            }
        });
    }

    public void modify(View view) {
        meters.document(documentId).update("address", address.getText().toString());
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}