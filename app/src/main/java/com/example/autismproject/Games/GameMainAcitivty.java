package com.example.autismproject.Games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.autismproject.R;

public class GameMainAcitivty extends AppCompatActivity {

    CardView color, number, alphabet, week, shape;
    //    LinearLayout poem;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_main_acitivty);

        color = findViewById(R.id.color);
        number = findViewById(R.id.number);
        alphabet = findViewById(R.id.alphabet);
        week = findViewById(R.id.week);
        shape = findViewById(R.id.shape);
//        poem = findViewById(R.id.lil_poem);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });


        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo create color screen
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo create number screen
            }
        });

        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo create alphabet screen
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo create week screen
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo create shape screen
            }
        });

//        poem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(GameMainAcitivty.this, PoemsActivity.class));
//            }
//        });


    }

}