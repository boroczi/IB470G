package com.example.vizora;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MeterAdapter extends RecyclerView.Adapter<MeterAdapter.ViewHolder> {
    private static final int KEY = 4444;
    private ArrayList<Meter> itemList;
    private Context context;
    private int lastPosition = -1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private CollectionReference meters;

    MeterAdapter(Context context, ArrayList<Meter> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.firestore = FirebaseFirestore.getInstance();
        this.meters = firestore.collection("items");

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeterAdapter.ViewHolder holder, int position) {
        Meter currentItem = itemList.get(position);
        holder.bindTo(currentItem);

        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private String documentId;
        private TextView mAddress;
        private TextView mLatestValue;
        private TextView mLatestDate;
        private TextView mDeadline;

        public void setDocumentId(String id) {
            this.documentId = id;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mAddress = itemView.findViewById(R.id.address);
            mLatestValue = itemView.findViewById(R.id.latestValue);
            mLatestDate = itemView.findViewById(R.id.latestDate);
            mDeadline = itemView.findViewById(R.id.deadline);

            itemView.findViewById(R.id.delete).setOnClickListener(
                    v -> {
                        MeterAdapter.this.itemList.remove(getAdapterPosition());
                        meters.document(documentId).delete();
                        MeterAdapter.this.notifyItemRemoved(getAdapterPosition());
                    }
            );

            itemView.findViewById(R.id.modify).setOnClickListener(
                    v -> {
                        Intent intent = new Intent(context, ModifyActivity.class);
                        intent.putExtra("KEY", KEY);
                        intent.putExtra("ID", documentId);
                        context.startActivity(intent);
                    }
            );
        }

        public void bindTo(Meter currentItem) {
            setDocumentId(currentItem.getDocumentId());
            mAddress.setText(currentItem.getAddress());
            mLatestValue.setText(String.valueOf(currentItem.getLatestValue()));
            mLatestDate.setText(currentItem.getLatestDate().toString());
            mDeadline.setText(currentItem.getDeadline().toString());
        }
    }
}
