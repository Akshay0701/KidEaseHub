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

import com.example.autismproject.Adapters.NumberAdapter;
import com.example.autismproject.Models.NumberHelper;
import com.example.autismproject.R;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity implements RecyclerViewAction{

    RecyclerView recyclerViewNumber;
    RecyclerView.Adapter adapter;

    static MediaPlayer mpone;
    static MediaPlayer mptwo;
    static MediaPlayer mpthree;
    static MediaPlayer mpfour;
    static MediaPlayer mpfive;
    static MediaPlayer mpsix;
    static MediaPlayer mpseven;
    static MediaPlayer mpeight;
    static MediaPlayer mpnine;
    static MediaPlayer mpten;
    static MediaPlayer mpeleven;
    static MediaPlayer mptwelve;
    static MediaPlayer mpthrten;
    static MediaPlayer mpfouteen;
    static MediaPlayer mpfifthen;
    static MediaPlayer mpsixten;
    static MediaPlayer mpseventen;
    static MediaPlayer mpeighten;
    static MediaPlayer mpninten;
    static MediaPlayer mptwenty;

    ImageView backMenu;
    TextView colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_numbers);

        recyclerViewNumber = findViewById(R.id.recycler_numbers);
        backMenu = findViewById(R.id.menu_nav);
        colorName = findViewById(R.id.number_name);

        mpone = MediaPlayer.create(NumbersActivity.this, R.raw.kid_1);
        mptwo= MediaPlayer.create(NumbersActivity.this, R.raw.kid_2);
        mpthree= MediaPlayer.create(NumbersActivity.this, R.raw.kid_3);
        mpfour= MediaPlayer.create(NumbersActivity.this, R.raw.kid_4);
        mpfive= MediaPlayer.create(NumbersActivity.this, R.raw.kid_5);
        mpsix= MediaPlayer.create(NumbersActivity.this, R.raw.kid_6);
        mpseven= MediaPlayer.create(NumbersActivity.this, R.raw.kid_7);
        mpeight= MediaPlayer.create(NumbersActivity.this, R.raw.kid_8);
        mpnine= MediaPlayer.create(NumbersActivity.this, R.raw.kid_9);
        mpten= MediaPlayer.create(NumbersActivity.this, R.raw.kid_10);
        mpeleven = MediaPlayer.create(NumbersActivity.this, R.raw.eleven);
        mptwelve= MediaPlayer.create(NumbersActivity.this, R.raw.twelve);
        mpthrten= MediaPlayer.create(NumbersActivity.this, R.raw.thirteen);
        mpfouteen= MediaPlayer.create(NumbersActivity.this, R.raw.fourteen);
        mpfifthen= MediaPlayer.create(NumbersActivity.this, R.raw.fifteen);
        mpsixten= MediaPlayer.create(NumbersActivity.this, R.raw.sixteen);
        mpseventen= MediaPlayer.create(NumbersActivity.this, R.raw.seventeen);
        mpeighten= MediaPlayer.create(NumbersActivity.this, R.raw.eighteen);
        mpninten= MediaPlayer.create(NumbersActivity.this, R.raw.nineteen);
        mptwenty= MediaPlayer.create(NumbersActivity.this, R.raw.twenty);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumbersActivity.super.onBackPressed();
            }
        });

        featuredNumbers();
    }

    private void featuredNumbers() {
        ArrayList<NumberHelper> questionLocations = new ArrayList<>();
        questionLocations.add(new NumberHelper("1"));
        questionLocations.add(new NumberHelper("2"));
        questionLocations.add(new NumberHelper("3"));
        questionLocations.add(new NumberHelper("4"));
        questionLocations.add(new NumberHelper("5"));
        questionLocations.add(new NumberHelper("6"));
        questionLocations.add(new NumberHelper("7"));
        questionLocations.add(new NumberHelper("8"));
        questionLocations.add(new NumberHelper("9"));
        questionLocations.add(new NumberHelper("10"));
        questionLocations.add(new NumberHelper("11"));
        questionLocations.add(new NumberHelper("12"));
        questionLocations.add(new NumberHelper("13"));
        questionLocations.add(new NumberHelper("14"));
        questionLocations.add(new NumberHelper("15"));
        questionLocations.add(new NumberHelper("16"));
        questionLocations.add(new NumberHelper("17"));
        questionLocations.add(new NumberHelper("18"));
        questionLocations.add(new NumberHelper("19"));
        questionLocations.add(new NumberHelper("20"));

        adapter = new NumberAdapter(questionLocations, this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewNumber.setLayoutManager(gridLayoutManager);
        recyclerViewNumber.setAdapter(adapter);
    }

    @Override
    public void onViewClicked(int clickedViewId, int clickedItemPosition) {
        try {
            switch (clickedItemPosition) {
                case 0:
                    mpone.start();
                    colorName.setText("One");
                    break;
                case 1:
                    mptwo.start();
                    colorName.setText("Two");
                    break;
                case 2:
                    mpthree.start();
                    colorName.setText("Three");
                    break;
                case 3:
                    mpfour.start();
                    colorName.setText("Four");
                    break;
                case 4:
                    mpfive.start();
                    colorName.setText("Five");
                    break;
                case 5:
                    mpsix.start();
                    colorName.setText("Six");
                    break;
                case 6:
                    mpseven.start();
                    colorName.setText("Seven");
                    break;
                case 7:
                    mpeight.start();
                    colorName.setText("Eight");
                    break;
                case 8:
                    mpnine.start();
                    colorName.setText("Nine");
                    break;
                case 9:
                    mpten.start();
                    colorName.setText("Ten");
                    break;
                case 10:
                    mpeleven.start();
                    colorName.setText("Eleven");
                    break;
                case 11:
                    mptwelve.start();
                    colorName.setText("Twelve");
                    break;
                case 12:
                    mpthrten.start();
                    colorName.setText("Thirteen");
                    break;
                case 13:
                    mpfouteen.start();
                    colorName.setText("Fourteen");
                    break;
                case 14:
                    mpfifthen.start();
                    colorName.setText("Fifteen");
                    break;
                case 15:
                    mpsixten.start();
                    colorName.setText("Sixteen");
                    break;
                case 16:
                    mpseventen.start();
                    colorName.setText("Seventeen");
                    break;
                case 17:
                    mpeighten.start();
                    colorName.setText("Eighteen");
                    break;
                case 18:
                    mpninten.start();
                    colorName.setText("Nineteen");
                    break;
                case 19:
                    mptwenty.start();
                    colorName.setText("Twenty");
                    break;
                default:
                    Toast.makeText(this, "Wrong index", Toast.LENGTH_SHORT).show();
                    colorName.setText("1 2 3");
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
        mpnine.release();
        mpten.release();
        mpeleven.release();
        mptwelve.release();
        mpthrten.release();
        mpfouteen.release();
        mpfifthen.release();
        mpsixten.release();
        mpseventen.release();
        mpeighten.release();
        mpninten.release();
        mptwenty.release();
    }

}
















