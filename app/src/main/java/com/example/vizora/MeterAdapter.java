package com.example.vizora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeterAdapter extends RecyclerView.Adapter<MeterAdapter.ViewHolder> {
    private ArrayList<Meter> itemList;
    private Context context;
    private int lastPosition = -1;

    MeterAdapter(Context context, ArrayList<Meter> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mID;
        private TextView mAddress;
        private TextView mLatestValue;
        private TextView mLatestDate;
        private TextView mDeadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mID = itemView.findViewById(R.id.id);
            mAddress = itemView.findViewById(R.id.address);
            mLatestValue = itemView.findViewById(R.id.latestValue);
            mLatestDate = itemView.findViewById(R.id.latestDate);
            mDeadline = itemView.findViewById(R.id.deadline);

            itemView.findViewById(R.id.save).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                }
            );
        }

        public void bindTo(Meter currentItem) {
            mAddress.setText(currentItem.getAddress());
            mLatestValue.setText(String.valueOf(currentItem.getLatestValue()));
            mLatestDate.setText(currentItem.getLatestDate().toString());
            mDeadline.setText(currentItem.getDeadline().toString());
        }
    }
}
