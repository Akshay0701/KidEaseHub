package com.example.autismproject;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.autismproject.Child.ChildClickBoard;
import com.example.autismproject.Models.Category;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}) // Adjust SDK version as needed
public class ChildClickBoardTest {
    private ChildClickBoard activity;
    private ActivityController<ChildClickBoard> controller;

    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private FirebaseUser mockFirebaseUser;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private DatabaseReference mockDatabaseReferenceCategory;
    @Mock
    private DatabaseReference mockDatabaseReferenceItem;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mockFirebaseDatabase.getReference("Categories")).thenReturn(mockDatabaseReferenceCategory);
        when(mockFirebaseDatabase.getReference("Items")).thenReturn(mockDatabaseReferenceItem);
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseUser.getUid()).thenReturn("12345");
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        controller = Robolectric.buildActivity(ChildClickBoard.class);
        activity = controller.get();

        activity.firebaseDatabase = mockFirebaseDatabase;
        activity.mAuth = mockFirebaseAuth;
        activity.pref = mockSharedPreferences;

        controller.create().start().resume();
    }

    @Test
    public void testLoadCategory() {
        // Mock the DatabaseReference and setup an Answer to simulate listener behavior
        when(mockDatabaseReferenceCategory.addValueEventListener((ValueEventListener) any(ValueEventListener.class))).thenAnswer((Answer<Void>) invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            DataSnapshot snapshot = mock(DataSnapshot.class);

            // Create and configure mock categories
            List<Category> testCategories = new ArrayList<>();
            Category cat = new Category("testUID", "Admin", "Category1","pid");
            testCategories.add(cat);
            when(snapshot.getChildren()).thenReturn((Iterable<DataSnapshot>) testCategories.iterator()); // Assume this method returns an Iterator
            when(snapshot.exists()).thenReturn(true);

            // Simulating the onDataChange callback
            listener.onDataChange(snapshot);
            return null;
        });

        // Call the method under test
        activity.loadCategory();

        // Assert that the categories are loaded correctly into the adapter
        assertNotNull(activity.adapterCategory);
        assertEquals(1, activity.adapterCategory.getItemCount());
    }

    @Test
    public void testClearSelectedItems() {
        activity.selectedItemText1.setText("Item 1");
        activity.selectedItemText2.setText("Item 2");
        activity.selectedItemText3.setText("Item 3");

        activity.clearSelectedItems();

        assertEquals("", activity.selectedItemText1.getText().toString());
        assertEquals("", activity.selectedItemText2.getText().toString());
        assertEquals("", activity.selectedItemText3.getText().toString());
    }

    @Test
    public void testSharedPreferencesListener() {
        when(mockSharedPreferences.getString("selectedCategoryID", "")).thenReturn("cat123");

        activity.listener.onSharedPreferenceChanged(mockSharedPreferences, "selectedCategoryID");

        verify(mockDatabaseReferenceItem).addValueEventListener((ValueEventListener) any(ValueEventListener.class));
    }
}

