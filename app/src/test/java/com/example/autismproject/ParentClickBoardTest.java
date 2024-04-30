package com.example.autismproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Adapters.AdapterCategory;
import com.example.autismproject.Adapters.AdapterItem;
import com.example.autismproject.Models.Category;
import com.example.autismproject.Models.Item;
import com.example.autismproject.Parent.ParentClickBoard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class ParentClickBoardTest {
    private ParentClickBoard activity;

    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private DatabaseReference mockDatabaseReferenceCategory;
    @Mock
    private DatabaseReference mockDatabaseReferenceItem;
    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private DataSnapshot mockDataSnapshot;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;
    @Mock
    private FirebaseUser mockFirebaseUser;  // Mock FirebaseUser as well




    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockFirebaseDatabase.getReference("Categories")).thenReturn(mockDatabaseReferenceCategory);
        when(mockFirebaseDatabase.getReference("Items")).thenReturn(mockDatabaseReferenceItem);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        activity = Robolectric.buildActivity(ParentClickBoard.class)
                .create()
                .resume()
                .get();
        activity.firebaseDatabase = mockFirebaseDatabase;
        activity.pref = mockSharedPreferences;
        activity.mAuth = mockFirebaseAuth;
    }

    @Test
    public void testLoadCategories_WithValidData() {
        // Mocking FirebaseAuth
        when(mockFirebaseAuth.getUid()).thenReturn("testUID");
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseAuth.getCurrentUser().getEmail()).thenReturn("test@example.com");

        // Setup DataSnapshot
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("testUID", "Admin", "Category1","pid"));
        // Mock the DataSnapshot to simulate Firebase data structure
        List<DataSnapshot> snapshots = new ArrayList<>();
        for (Category category : categories) {
            DataSnapshot snapshot = mock(DataSnapshot.class);
            when(snapshot.getValue(Category.class)).thenReturn(category);
            snapshots.add(snapshot);
        }

        // Use an Answer to return an iterable over snapshots
        when(mockDataSnapshot.getChildren()).thenAnswer(new Answer<Iterable<DataSnapshot>>() {
            @Override
            public Iterable<DataSnapshot> answer(InvocationOnMock invocation) throws Throwable {
                return snapshots;
            }
        });


        // Trigger
        activity.loadCategory();

        // Validate
        verify(mockDatabaseReferenceCategory).addValueEventListener(any());
    }

    @Test
    public void clickAddNewItem_StartsNewActivity() {
        Button addNewItem = activity.findViewById(R.id.parent_clickboard_additem);
        addNewItem.performClick();
        Intent expectedIntent = new Intent(activity, AddNewItem.class);
        // Assert that the expected Intent was started
        //assertThat(shadowOf(RuntimeEnvironment.application).getNextStartedActivity())
                //.isEqualTo(expectedIntent);

        assertEquals(expectedIntent,shadowOf(RuntimeEnvironment.application).getNextStartedActivity());
    }

    // Additional tests can be implemented similarly
}
