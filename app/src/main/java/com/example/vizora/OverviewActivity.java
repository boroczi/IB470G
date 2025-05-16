package com.example.vizora;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int KEY = 4444;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ArrayList<Meter> itemList;
    private MeterAdapter adapter;

    private FirebaseFirestore firestore;
    private CollectionReference meters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("KEY", 0) != KEY) {
            finish();
        }

        this.mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
        }

        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.itemList = new ArrayList<>();
        this.adapter = new MeterAdapter(this, itemList);
        this.recyclerView.setAdapter(adapter);

        this.firestore = FirebaseFirestore.getInstance();
        this.meters = firestore.collection("items");

        query();
    }

    private void query() {
        this.itemList.clear();

        this.meters.whereEqualTo("owner", user.getEmail()).get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Meter meter = document.toObject(Meter.class);
                        meter.setDocumentId(document.getId());
                        this.itemList.add(meter);
                    }
                    this.adapter.notifyDataSetChanged();
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            finish();
            return true;
        } else if (item.getItemId() == R.id.add) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}