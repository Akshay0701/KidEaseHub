package com.example.autismproject;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Adapters.WeekAdapter;
import com.example.autismproject.Games.WeekActivity;
import com.example.autismproject.Models.WeekHelper;
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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Config.OLDEST_SDK, Config.NEWEST_SDK})
public class WeekActivityTest {

    private WeekActivity weekActivity;

    @Before
    public void setUp() {
        weekActivity = Robolectric.buildActivity(WeekActivity.class).create().resume().get();
    }

    @After
    public void tearDown() {
        weekActivity.finish();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(weekActivity);
    }

    @Test
    public void testRecyclerViewNotNull() {
        RecyclerView recyclerView = weekActivity.findViewById(R.id.recycler_week);
        assertNotNull(recyclerView);
    }

    @Test
    public void testMediaPlayerInitialized() {
        assertNotNull(WeekActivity.mpone);
        assertNotNull(WeekActivity.mptwo);
        assertNotNull(WeekActivity.mpthree);
        assertNotNull(WeekActivity.mpfour);
        assertNotNull(WeekActivity.mpfive);
        assertNotNull(WeekActivity.mpsix);
        assertNotNull(WeekActivity.mpseven);
    }

    @Test
    public void testRecyclerViewAdapterNotNull() {
        RecyclerView recyclerView = weekActivity.findViewById(R.id.recycler_week);
        RecyclerView.Adapter adapter =recyclerView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void testBackMenuNotNull() {
        ImageView backMenu = weekActivity.findViewById(R.id.menu_nav);
        assertNotNull(backMenu);
    }

    @Test
    public void testColorNameTextViewNotNull() {
        TextView colorName = weekActivity.findViewById(R.id.week_number);
        assertNotNull(colorName);
    }

}

