package com.example.vizora;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class AddRecordActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference meters;
    private EditText latestValue;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_record);
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

        this.latestValue = findViewById(R.id.EditTextRecord);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
        }

        this.firestore = FirebaseFirestore.getInstance();
        this.meters = firestore.collection("items");

        this.documentId = getIntent().getStringExtra("ID");
    }

    public void modify(View view) {
        meters.document(documentId).update("latestValue", Float.valueOf(latestValue.getText().toString()));
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}