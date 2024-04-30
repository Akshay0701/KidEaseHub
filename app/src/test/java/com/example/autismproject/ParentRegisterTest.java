package com.example.autismproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autismproject.Parent.ParentLogin;
import com.example.autismproject.Parent.ParentRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class ParentRegisterTest {

    private ParentRegister activity;

    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private FirebaseUser mockFirebaseUser;
    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.buildActivity(ParentRegister.class)
                .create()
                .resume()
                .get();
        activity.mAuth = mockAuth;
        activity.parentEmail = mock(EditText.class);
        activity.parentName = mock(EditText.class);
        activity.parentPassword = mock(EditText.class);
        activity.parentPhoneno = mock(EditText.class);
        when(mockAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseDatabase.getReference("Parents")).thenReturn(mockDatabaseReference);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
    }

    @Test
    public void registerUser_WithValidData() {
        // Setup
        when(activity.parentEmail.getText().toString()).thenReturn("test@example.com");
        when(activity.parentName.getText().toString()).thenReturn("Test Name");
        when(activity.parentPassword.getText().toString()).thenReturn("123456");
        when(activity.parentPhoneno.getText().toString()).thenReturn("1234567890");

        doNothing().when(mockEditor).putString(anyString(), anyString());
        when(mockEditor.commit()).thenReturn(true);

        // Execute
        activity.findViewById(R.id.parent_register_Btn).performClick();

        // Verify
        verify(mockAuth).createUserWithEmailAndPassword("test@example.com", "123456");
    }

    @Test
    public void onClickGotoLogin_StartsLoginActivity() {
        activity.findViewById(R.id.parent_register_gotoLogin).performClick();
        Intent expectedIntent = new Intent(activity, ParentLogin.class);
        assertEquals(expectedIntent.getComponent(), shadowOf(activity).getNextStartedActivity().getComponent());
    }
}
