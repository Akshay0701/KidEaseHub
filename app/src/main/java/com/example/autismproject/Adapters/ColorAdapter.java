package com.example.autismproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Games.RecyclerViewAction;
import com.example.autismproject.Models.ColorHelper;
import com.example.autismproject.R;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>{
    ArrayList<ColorHelper> color;
    private Context mContext;
    private RecyclerViewAction recyclerViewAction;

    public ColorAdapter(ArrayList<ColorHelper> color, Context mContext, RecyclerViewAction recyclerViewAction) {
        this.color = color;
        this.mContext = mContext;
        this.recyclerViewAction = recyclerViewAction;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allcolors, parent, false);
        ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        final ColorHelper colorHelper = color.get(position);

        holder.title.setText(colorHelper.getTitle());
        holder.linearLayout.setBackground(colorHelper.getGradient());
        if (holder.title.getText().toString().equals("White")) {
            holder.title.setTextColor(Color.parseColor("#000000"));
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
        return color.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public View layout;
        CardView container;
        LinearLayout linearLayout;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            //Hooks
            title = itemView.findViewById(R.id.content);
            linearLayout = itemView.findViewById(R.id.linear_color);
        }
    }
}























