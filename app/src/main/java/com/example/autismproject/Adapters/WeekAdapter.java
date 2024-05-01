package com.example.autismproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Games.RecyclerViewAction;
import com.example.autismproject.Models.WeekHelper;
import com.example.autismproject.R;

import java.util.ArrayList;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ColorViewHolder>{
    ArrayList<WeekHelper> week;
    private Context mContext;
    private RecyclerViewAction recyclerViewAction;

    public WeekAdapter(ArrayList<WeekHelper> week, Context mContext, RecyclerViewAction recyclerViewAction) {
        this.week = week;
        this.mContext = mContext;
        this.recyclerViewAction = recyclerViewAction;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);
        ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        final WeekHelper weekHelper = week.get(position);

        holder.title.setText(weekHelper.getTitle());
        if (holder.title.getText().toString().equals("Sunday")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("Monday")) {
            holder.title.setTextColor(Color.parseColor("#4caf50"));
        }
        if (holder.title.getText().toString().equals("Tuesday")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("Wednesday")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }
        if (holder.title.getText().toString().equals("Thursday")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("Friday")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("Saturday")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAction.onViewClicked(v.getId(), holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return week.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public View layout;
        CardView container;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            //Hooks
            title = itemView.findViewById(R.id.content);

        }
    }
}























