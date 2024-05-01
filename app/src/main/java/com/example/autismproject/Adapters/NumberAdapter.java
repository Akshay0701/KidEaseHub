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
import com.example.autismproject.Models.NumberHelper;
import com.example.autismproject.R;

import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ColorViewHolder>{
    ArrayList<NumberHelper> number;
    private Context mContext;
    private RecyclerViewAction recyclerViewAction;

    public NumberAdapter(ArrayList<NumberHelper> number, Context mContext, RecyclerViewAction recyclerViewAction) {
        this.number = number;
        this.mContext = mContext;
        this.recyclerViewAction = recyclerViewAction;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allnumbers, parent, false);
        ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        final NumberHelper numberHelper = number.get(position);

        holder.title.setText(numberHelper.getTitle());
        if (holder.title.getText().toString().equals("1")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("2")) {
            holder.title.setTextColor(Color.parseColor("#4caf50"));
        }
        if (holder.title.getText().toString().equals("3")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("4")) {
            holder.title.setTextColor(Color.parseColor("#4caf50"));
        }
        if (holder.title.getText().toString().equals("5")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("6")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("7")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }
        if (holder.title.getText().toString().equals("8")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("9")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }
        if (holder.title.getText().toString().equals("10")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("11")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("12")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }
        if (holder.title.getText().toString().equals("13")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("14")) {
            holder.title.setTextColor(Color.parseColor("#ec407a"));
        }
        if (holder.title.getText().toString().equals("15")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("16")) {
            holder.title.setTextColor(Color.parseColor("#2196f3"));
        }
        if (holder.title.getText().toString().equals("17")) {
            holder.title.setTextColor(Color.parseColor("#4caf50"));
        }
        if (holder.title.getText().toString().equals("18")) {
            holder.title.setTextColor(Color.parseColor("#4caf50"));
        }
        if (holder.title.getText().toString().equals("19")) {
            holder.title.setTextColor(Color.parseColor("#9c27b0"));
        }
        if (holder.title.getText().toString().equals("20")) {
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
        return number.size();
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























