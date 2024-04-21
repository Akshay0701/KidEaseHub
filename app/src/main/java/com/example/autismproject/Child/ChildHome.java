package com.example.autismproject.Child;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.GameManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.autismproject.Games.GameMainAcitivty;
import com.example.autismproject.R;

public class ChildHome extends AppCompatActivity {

    CardView clickBoardCarView, gamesCardView, todoListCardView, videosCardView, mCQCardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);

        mCQCardView = findViewById(R.id.card_MCQ);
        // todo make a mcq test

        clickBoardCarView = findViewById(R.id.card_clickboard);
        clickBoardCarView.setOnClickListener(view -> {
           startActivity(new Intent(ChildHome.this, ChildClickBoard.class));
        });

        gamesCardView = findViewById(R.id.card_gaming);
        gamesCardView.setOnClickListener(view -> {
            startActivity(new Intent(ChildHome.this, GameMainAcitivty.class));
        });

        todoListCardView = findViewById(R.id.card_todolist);
        todoListCardView.setOnClickListener(view -> {
            // todo make a child todo
        });

        videosCardView = findViewById(R.id.card_youtubeVideos);
        videosCardView.setOnClickListener(view -> {
            startActivity(new Intent(ChildHome.this, ChildVideos.class));
        });
    }
}