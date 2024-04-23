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

import com.example.autismproject.Adapters.AlphabetAdapter;
import com.example.autismproject.Models.AlphabetHelper;
import com.example.autismproject.R;

import java.util.ArrayList;

public class AlphabetActivity extends AppCompatActivity implements RecyclerViewAction{

    RecyclerView recyclerViewAlphabet;
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
    static MediaPlayer mptwentyone;
    static MediaPlayer mptwentytwo;
    static MediaPlayer mptwentythree;
    static MediaPlayer mptwentyfour;
    static MediaPlayer mptwentyfive;
    static MediaPlayer mptwentysix;

    ImageView backMenu;
    TextView colorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alphabet);

        recyclerViewAlphabet = findViewById(R.id.recycler_alphabet);
        backMenu = findViewById(R.id.menu_nav);
        colorName = findViewById(R.id.alphabet_number);

        mpone = MediaPlayer.create(AlphabetActivity.this, R.raw.a);
        mptwo= MediaPlayer.create(AlphabetActivity.this, R.raw.b);
        mpthree= MediaPlayer.create(AlphabetActivity.this, R.raw.c);
        mpfour= MediaPlayer.create(AlphabetActivity.this, R.raw.d);
        mpfive= MediaPlayer.create(AlphabetActivity.this, R.raw.e);
        mpsix= MediaPlayer.create(AlphabetActivity.this, R.raw.f);
        mpseven= MediaPlayer.create(AlphabetActivity.this, R.raw.g);
        mpeight= MediaPlayer.create(AlphabetActivity.this, R.raw.h);
        mpnine= MediaPlayer.create(AlphabetActivity.this, R.raw.i);
        mpten= MediaPlayer.create(AlphabetActivity.this, R.raw.j);
        mpeleven = MediaPlayer.create(AlphabetActivity.this, R.raw.k);
        mptwelve= MediaPlayer.create(AlphabetActivity.this, R.raw.l);
        mpthrten= MediaPlayer.create(AlphabetActivity.this, R.raw.m);
        mpfouteen= MediaPlayer.create(AlphabetActivity.this, R.raw.n);
        mpfifthen= MediaPlayer.create(AlphabetActivity.this, R.raw.o);
        mpsixten= MediaPlayer.create(AlphabetActivity.this, R.raw.p);
        mpseventen= MediaPlayer.create(AlphabetActivity.this, R.raw.q);
        mpeighten= MediaPlayer.create(AlphabetActivity.this, R.raw.r);
        mpninten= MediaPlayer.create(AlphabetActivity.this, R.raw.s);
        mptwenty= MediaPlayer.create(AlphabetActivity.this, R.raw.t);

        mptwentyone= MediaPlayer.create(AlphabetActivity.this, R.raw.u);
        mptwentytwo= MediaPlayer.create(AlphabetActivity.this, R.raw.v);
        mptwentythree= MediaPlayer.create(AlphabetActivity.this, R.raw.w);
        mptwentyfour= MediaPlayer.create(AlphabetActivity.this, R.raw.x);
        mptwentyfive= MediaPlayer.create(AlphabetActivity.this, R.raw.y);
        mptwentysix= MediaPlayer.create(AlphabetActivity.this, R.raw.z);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphabetActivity.super.onBackPressed();
            }
        });

        featuredAlphabet();

    }

    private void featuredAlphabet() {
        ArrayList<AlphabetHelper> questionLocations = new ArrayList<>();
        questionLocations.add(new AlphabetHelper("A"));
        questionLocations.add(new AlphabetHelper("B"));
        questionLocations.add(new AlphabetHelper("C"));
        questionLocations.add(new AlphabetHelper("D"));
        questionLocations.add(new AlphabetHelper("E"));
        questionLocations.add(new AlphabetHelper("F"));
        questionLocations.add(new AlphabetHelper("G"));
        questionLocations.add(new AlphabetHelper("H"));
        questionLocations.add(new AlphabetHelper("I"));
        questionLocations.add(new AlphabetHelper("J"));
        questionLocations.add(new AlphabetHelper("K"));
        questionLocations.add(new AlphabetHelper("L"));
        questionLocations.add(new AlphabetHelper("M"));
        questionLocations.add(new AlphabetHelper("N"));
        questionLocations.add(new AlphabetHelper("O"));
        questionLocations.add(new AlphabetHelper("P"));
        questionLocations.add(new AlphabetHelper("Q"));
        questionLocations.add(new AlphabetHelper("R"));
        questionLocations.add(new AlphabetHelper("S"));
        questionLocations.add(new AlphabetHelper("T"));
        questionLocations.add(new AlphabetHelper("U"));
        questionLocations.add(new AlphabetHelper("V"));
        questionLocations.add(new AlphabetHelper("W"));
        questionLocations.add(new AlphabetHelper("X"));
        questionLocations.add(new AlphabetHelper("Y"));
        questionLocations.add(new AlphabetHelper("Z"));

        adapter = new AlphabetAdapter(questionLocations, this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerViewAlphabet.setLayoutManager(gridLayoutManager);
        recyclerViewAlphabet.setAdapter(adapter);
    }

    @Override
    public void onViewClicked(int clickedViewId, int clickedItemPosition) {
        try {
            switch (clickedItemPosition) {
                case 0:
                    mpone.start();
                    colorName.setText("A");
                    break;
                case 1:
                    mptwo.start();
                    colorName.setText("B");
                    break;
                case 2:
                    mpthree.start();
                    colorName.setText("C");
                    break;
                case 3:
                    mpfour.start();
                    colorName.setText("D");
                    break;
                case 4:
                    mpfive.start();
                    colorName.setText("E");
                    break;
                case 5:
                    mpsix.start();
                    colorName.setText("F");
                    break;
                case 6:
                    mpseven.start();
                    colorName.setText("G");
                    break;
                case 7:
                    mpeight.start();
                    colorName.setText("H");
                    break;
                case 8:
                    mpnine.start();
                    colorName.setText("I");
                    break;
                case 9:
                    mpten.start();
                    colorName.setText("J");
                    break;
                case 10:
                    mpeleven.start();
                    colorName.setText("K");
                    break;
                case 11:
                    mptwelve.start();
                    colorName.setText("L");
                    break;
                case 12:
                    mpthrten.start();
                    colorName.setText("M");
                    break;
                case 13:
                    mpfouteen.start();
                    colorName.setText("N");
                    break;
                case 14:
                    mpfifthen.start();
                    colorName.setText("O");
                    break;
                case 15:
                    mpsixten.start();
                    colorName.setText("P");
                    break;
                case 16:
                    mpseventen.start();
                    colorName.setText("Q");
                    break;
                case 17:
                    mpeighten.start();
                    colorName.setText("R");
                    break;
                case 18:
                    mpninten.start();
                    colorName.setText("S");
                    break;
                case 19:
                    mptwenty.start();
                    colorName.setText("T");
                    break;
                case 20:
                    mptwentyone.start();
                    colorName.setText("U");
                    break;
                case 21:
                    mptwentytwo.start();
                    colorName.setText("V");
                    break;
                case 22:
                    mptwentythree.start();
                    colorName.setText("W");
                    break;
                case 23:
                    mptwentyfour.start();
                    colorName.setText("X");
                    break;
                case 24:
                    mptwentyfive.start();
                    colorName.setText("Y");
                    break;
                case 25:
                    mptwentysix.start();
                    colorName.setText("Z");
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
        mptwentyone.release();
        mptwentytwo.release();
        mptwentythree.release();
        mptwentyfour.release();
        mptwentyfive.release();
        mptwentysix.release();
    }

}