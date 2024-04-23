package com.example.autismproject.Games;

import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Adapters.ColorAdapter;
import com.example.autismproject.Models.ColorHelper;
import com.example.autismproject.R;

import java.util.ArrayList;


public class ColorsActivity extends AppCompatActivity implements RecyclerViewAction{

    RecyclerView recyclerViewColor;
    RecyclerView.Adapter adapter;
    private GradientDrawable red, green, purple, blue, pink, yellow, orange, brown, black, white;

    //Media Creation
    static MediaPlayer mppurple;
    static MediaPlayer mpwhite;
    static MediaPlayer mpgreen;
    static MediaPlayer mppink;
    static MediaPlayer mpred;
    static MediaPlayer mpblue;
    static MediaPlayer mpyellow;
    static MediaPlayer mpbrown;
    static MediaPlayer mporange;
    static MediaPlayer mpblack;

    ImageView backMenu;
    TextView colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_colors);

        recyclerViewColor = findViewById(R.id.recycler_color);
        backMenu = findViewById(R.id.menu_nav);
        colorName = findViewById(R.id.color_name);

        mpblue = MediaPlayer.create(ColorsActivity.this, R.raw.blue);
        mpgreen= MediaPlayer.create(ColorsActivity.this, R.raw.green);
        mppink= MediaPlayer.create(ColorsActivity.this, R.raw.pink);
        mppurple= MediaPlayer.create(ColorsActivity.this, R.raw.purple);
        mpwhite= MediaPlayer.create(ColorsActivity.this, R.raw.white);
        mpred= MediaPlayer.create(ColorsActivity.this, R.raw.red);
        mpyellow= MediaPlayer.create(ColorsActivity.this, R.raw.yellow);
        mporange= MediaPlayer.create(ColorsActivity.this, R.raw.orange);
        mpbrown= MediaPlayer.create(ColorsActivity.this, R.raw.brown);
        mpblack= MediaPlayer.create(ColorsActivity.this, R.raw.black);

        featuredColors();

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorsActivity.super.onBackPressed();
            }
        });

    }

    private void featuredColors() {
        //All Gradients
        red = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xccf44336, 0xccf44336});
        green = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xcc4caf50, 0xcc4caf50});
        purple = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xcc9c27b0, 0xcc9c27b0});
        blue = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xcc2196f3, 0xcc2196f3});
        pink = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xccec407a, 0xccec407a});
        yellow = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xccffeb3b, 0xccffeb3b});
        orange = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xccff9800, 0xccff9800});
        brown = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xcc795548, 0xcc795548});
        black = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xcc000000, 0xcc000000});
        white = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xccffffff, 0xccffffff});

        ArrayList<ColorHelper> questionLocations = new ArrayList<>();
        questionLocations.add(new ColorHelper(red,"Red"));
        questionLocations.add(new ColorHelper(green,"Green"));
        questionLocations.add(new ColorHelper(purple,"Purple"));
        questionLocations.add(new ColorHelper(blue,"Blue"));
        questionLocations.add(new ColorHelper(pink,"Pink"));
        questionLocations.add(new ColorHelper(yellow,"Yellow"));
        questionLocations.add(new ColorHelper(orange,"Orange"));
        questionLocations.add(new ColorHelper(brown,"Brown"));
        questionLocations.add(new ColorHelper(black, "Black"));
        questionLocations.add(new ColorHelper(white,"White"));

        adapter = new ColorAdapter(questionLocations, this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewColor.setLayoutManager(gridLayoutManager);
        recyclerViewColor.setAdapter(adapter);

    }

    @Override
    public void onViewClicked(int clickedViewId, int clickedItemPosition) {
        try {
            switch (clickedItemPosition) {
                case 0:
                    mpred.start();
                    colorName.setText("Red");
                    break;
                case 1:
                    mpgreen.start();
                    colorName.setText("Green");
                    break;
                case 2:
                    mppurple.start();
                    colorName.setText("Purple");
                    break;
                case 3:
                    mpblue.start();
                    colorName.setText("Blue");
                    break;
                case 4:
                    mppink.start();
                    colorName.setText("Pink");
                    break;
                case 5:
                    mpyellow.start();
                    colorName.setText("Yellow");
                    break;
                case 6:
                    mporange.start();
                    colorName.setText("Orange");
                    break;
                case 7:
                    mpbrown.start();
                    colorName.setText("Brown");
                    break;
                case 8:
                    mpblack.start();
                    colorName.setText("Black");
                    break;
                case 9:
                    mpwhite.start();
                    colorName.setText("White");
                    break;
                default:
                    Toast.makeText(this, "Wrong index", Toast.LENGTH_SHORT).show();
                    colorName.setText("Color");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewLongClicked(int clickedViewId, int clickedItemPosition) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mpblue.release();
        mpgreen.release();
        mppink.release();
        mppurple.release();
        mpwhite.release();
        mpred.release();
        mpyellow.release();
        mpwhite.release();
        mpbrown.release();
        mporange.release();
    }
}