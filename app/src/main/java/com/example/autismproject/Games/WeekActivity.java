package com.example.autismproject.Games;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Adapters.WeekAdapter;
import com.example.autismproject.Models.WeekHelper;
import com.example.autismproject.R;

import java.util.ArrayList;


public class WeekActivity extends AppCompatActivity implements RecyclerViewAction{

    RecyclerView recyclerViewWeek;
    RecyclerView.Adapter adapter;

    static MediaPlayer mpone;
    static MediaPlayer mptwo;
    static MediaPlayer mpthree;
    static MediaPlayer mpfour;
    static MediaPlayer mpfive;
    static MediaPlayer mpsix;
    static MediaPlayer mpseven;

    ImageView backMenu;
    TextView colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_week);

        recyclerViewWeek = findViewById(R.id.recycler_week);
        backMenu = findViewById(R.id.menu_nav);
        colorName = findViewById(R.id.week_number);

        mpone = MediaPlayer.create(WeekActivity.this, R.raw.sunday);
        mptwo= MediaPlayer.create(WeekActivity.this, R.raw.monday);
        mpthree= MediaPlayer.create(WeekActivity.this, R.raw.tuesday);
        mpfour= MediaPlayer.create(WeekActivity.this, R.raw.wednesday);
        mpfive= MediaPlayer.create(WeekActivity.this, R.raw.thursday);
        mpsix= MediaPlayer.create(WeekActivity.this, R.raw.friday);
        mpseven= MediaPlayer.create(WeekActivity.this, R.raw.saturday);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekActivity.super.onBackPressed();
            }
        });
        
        featuredWeek();
        
    }

    private void featuredWeek() {
        ArrayList<WeekHelper> questionLocations = new ArrayList<>();
        questionLocations.add(new WeekHelper("Sunday"));
        questionLocations.add(new WeekHelper("Monday"));
        questionLocations.add(new WeekHelper("Tuesday"));
        questionLocations.add(new WeekHelper("Wednesday"));
        questionLocations.add(new WeekHelper("Thursday"));
        questionLocations.add(new WeekHelper("Friday"));
        questionLocations.add(new WeekHelper("Saturday"));

        recyclerViewWeek.setHasFixedSize(true);
        recyclerViewWeek.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new WeekAdapter(questionLocations, this, this);
        recyclerViewWeek.setAdapter(adapter);
        
    }

    @Override
    public void onViewClicked(int clickedViewId, int clickedItemPosition) {
        try {
            switch (clickedItemPosition) {
                case 0:
                    mpone.start();
                    colorName.setText("Sunday");
                    break;
                case 1:
                    mptwo.start();
                    colorName.setText("Monday");
                    break;
                case 2:
                    mpthree.start();
                    colorName.setText("Tuesday");
                    break;
                case 3:
                    mpfour.start();
                    colorName.setText("Wednesday");
                    break;
                case 4:
                    mpfive.start();
                    colorName.setText("Thursday");
                    break;
                case 5:
                    mpsix.start();
                    colorName.setText("Friday");
                    break;
                case 6:
                    mpseven.start();
                    colorName.setText("Saturday");
                    break;
                default:
                    Toast.makeText(this, "Wrong index", Toast.LENGTH_SHORT).show();
                    colorName.setText("Weeks");
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
    }

}
