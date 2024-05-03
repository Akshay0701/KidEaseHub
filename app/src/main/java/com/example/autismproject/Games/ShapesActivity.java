package com.example.autismproject.Games;

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

import com.example.autismproject.Adapters.ShapeAdapter;
import com.example.autismproject.Models.ShapeHelper;
import com.example.autismproject.R;

import java.util.ArrayList;


public class ShapesActivity extends AppCompatActivity implements RecyclerViewAction{

    RecyclerView recyclerViewShape;
    RecyclerView.Adapter adapter;

    static MediaPlayer mpone;
    static MediaPlayer mptwo;
    static MediaPlayer mpthree;
    static MediaPlayer mpfour;
    static MediaPlayer mpfive;
    static MediaPlayer mpsix;
    static MediaPlayer mpseven;
    static MediaPlayer mpeight;

    ImageView backMenu;
    TextView colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shapes);

        recyclerViewShape = findViewById(R.id.recycler_shape);
        backMenu = findViewById(R.id.menu_nav);
        colorName = findViewById(R.id.shape_number);

        mpone = MediaPlayer.create(ShapesActivity.this, R.raw.circle);
        mptwo= MediaPlayer.create(ShapesActivity.this, R.raw.square);
        mpthree= MediaPlayer.create(ShapesActivity.this, R.raw.triangle);
        mpfour= MediaPlayer.create(ShapesActivity.this, R.raw.star);
        mpfive= MediaPlayer.create(ShapesActivity.this, R.raw.rectangle);
        mpsix= MediaPlayer.create(ShapesActivity.this, R.raw.oval);
        mpseven= MediaPlayer.create(ShapesActivity.this, R.raw.diamond);
        mpeight= MediaPlayer.create(ShapesActivity.this, R.raw.hexagon);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShapesActivity.super.onBackPressed();
            }
        });
        
        featuredShapes();

    }

    private void featuredShapes() {
        ArrayList<ShapeHelper> questionLocations = new ArrayList<>();
        questionLocations.add(new ShapeHelper(R.drawable.circle));
        questionLocations.add(new ShapeHelper(R.drawable.square));
        questionLocations.add(new ShapeHelper(R.drawable.triangle));
        questionLocations.add(new ShapeHelper(R.drawable.star));
        questionLocations.add(new ShapeHelper(R.drawable.rectangle));
        questionLocations.add(new ShapeHelper(R.drawable.oval));
        questionLocations.add(new ShapeHelper(R.drawable.diamond));
        questionLocations.add(new ShapeHelper(R.drawable.hexagon));

        adapter = new ShapeAdapter(questionLocations, this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewShape.setLayoutManager(gridLayoutManager);
        recyclerViewShape.setAdapter(adapter);
    }

    @Override
    public void onViewClicked(int clickedViewId, int clickedItemPosition) {
        try {
            switch (clickedItemPosition) {
                case 0:
                    mpone.start();
                    colorName.setText("Circle");
                    break;
                case 1:
                    mptwo.start();
                    colorName.setText("Square");
                    break;
                case 2:
                    mpthree.start();
                    colorName.setText("Triangle");
                    break;
                case 3:
                    mpfour.start();
                    colorName.setText("Star");
                    break;
                case 4:
                    mpfive.start();
                    colorName.setText("Rectangle");
                    break;
                case 5:
                    mpsix.start();
                    colorName.setText("Oval");
                    break;
                case 6:
                    mpseven.start();
                    colorName.setText("Diamond");
                    break;
                case 7:
                    mpeight.start();
                    colorName.setText("Hexagon");
                    break;
                default:
                    Toast.makeText(this, "Wrong index", Toast.LENGTH_SHORT).show();
                    colorName.setText("Shapes");
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
        mpone.release();
        mptwo.release();
        mpthree.release();
        mpfour.release();
        mpfive.release();
        mpsix.release();
        mpseven.release();
        mpeight.release();
    }

}