package com.example.autismproject;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Adapters.AlphabetAdapter;
import com.example.autismproject.Models.AlphabetHelper;
import com.example.autismproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Config.OLDEST_SDK, Config.NEWEST_SDK})
public class AlphabetActivityTest {

    private AlphabetActivity alphabetActivity;

    @Before
    public void setUp() {
        alphabetActivity = Robolectric.buildActivity(AlphabetActivity.class).create().resume().get();
    }

    @After
    public void tearDown() {
        alphabetActivity.finish();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(alphabetActivity);
    }

    @Test
    public void testRecyclerViewNotNull() {
        RecyclerView recyclerView = alphabetActivity.findViewById(R.id.recycler_alphabet);
        assertNotNull(recyclerView);
    }

    @Test
    public void testMediaPlayerInitialized() {
        assertNotNull(AlphabetActivity.mpone);
        assertNotNull(AlphabetActivity.mptwo);
        assertNotNull(AlphabetActivity.mpthree);
        assertNotNull(AlphabetActivity.mpfour);
        assertNotNull(AlphabetActivity.mpfive);
        assertNotNull(AlphabetActivity.mpsix);
        assertNotNull(AlphabetActivity.mpseven);
        assertNotNull(AlphabetActivity.mpeight);
        assertNotNull(AlphabetActivity.mpnine);
        assertNotNull(AlphabetActivity.mpten);
        assertNotNull(AlphabetActivity.mpeleven);
        assertNotNull(AlphabetActivity.mptwelve);
        assertNotNull(AlphabetActivity.mpthrten);
        assertNotNull(AlphabetActivity.mpfouteen);
        assertNotNull(AlphabetActivity.mpfifthen);
        assertNotNull(AlphabetActivity.mpsixten);
        assertNotNull(AlphabetActivity.mpseventen);
        assertNotNull(AlphabetActivity.mpeighten);
        assertNotNull(AlphabetActivity.mpninten);
        assertNotNull(AlphabetActivity.mptwenty);
        assertNotNull(AlphabetActivity.mptwentyone);
        assertNotNull(AlphabetActivity.mptwentytwo);
        assertNotNull(AlphabetActivity.mptwentythree);
        assertNotNull(AlphabetActivity.mptwentyfour);
        assertNotNull(AlphabetActivity.mptwentyfive);
        assertNotNull(AlphabetActivity.mptwentysix);
    }

    @Test
    public void testRecyclerViewAdapterNotNull() {
        RecyclerView recyclerView = alphabetActivity.findViewById(R.id.recycler_alphabet);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void testBackMenuNotNull() {
        ImageView backMenu = alphabetActivity.findViewById(R.id.menu_nav);
        assertNotNull(backMenu);
    }

    @Test
    public void testColorNameTextViewNotNull() {
        TextView colorName = alphabetActivity.findViewById(R.id.alphabet_number);
        assertNotNull(colorName);
    }

}

