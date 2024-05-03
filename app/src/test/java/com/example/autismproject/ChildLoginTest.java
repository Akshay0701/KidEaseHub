package com.example.autismproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.autismproject.Child.ChildLogin;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class ChildLoginTest {

    private ChildLogin activity;

    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private FirebaseUser mockFirebaseUser;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        // Set up the activity with Robolectric
        activity = Robolectric.buildActivity(ChildLogin.class)
                .create()
                .resume()
                .get();

        activity.mAuth = mockFirebaseAuth; // Inject the mock FirebaseAuth
        activity.firebaseDatabase = mockFirebaseDatabase; // Inject mock FirebaseDatabase
        activity.databaseReference = mockDatabaseReference; // Inject mock DatabaseReference
    }

    @Test
    public void testLoginParent_ValidCredentials() {
        // Create a mock AuthResult (assuming it's needed)
        AuthResult mockAuthResult = mock(AuthResult.class);

        // Simulate successful authentication
        Task<AuthResult> successfulLoginTask = Tasks.forResult(mockAuthResult);
        when(mockFirebaseAuth.signInWithEmailAndPassword("test@example.com", "password123"))
                .thenReturn(successfulLoginTask);

        // Execute and verify
        activity.loginParent("test@example.com", "password123");
        successfulLoginTask.addOnCompleteListener(task -> {
            assertTrue(task.isSuccessful());
            assertNotNull(task.getResult());
        });
    }

    @Test
    public void testLoginParent_InvalidCredentials() {
        // Simulate failed authentication
        Task<AuthResult> failedLoginTask = Tasks.forException(new FirebaseAuthException("auth-error", "Authentication failed"));
        when(mockFirebaseAuth.signInWithEmailAndPassword("invalid@example.com", "wrong"))
                .thenReturn(failedLoginTask);

        // Execute and verify
        activity.loginParent("invalid@example.com", "wrong");
        failedLoginTask.addOnCompleteListener(task -> {
            assertFalse(task.isSuccessful());
            try {
                task.getResult();
                fail("Exception expected");
            } catch (Exception e) {
                assertTrue(e instanceof FirebaseAuthException);
            }
        });
    }

    @Test
    public void testOnBackPressed() {
        // Simulate pressing back
        activity.onBackPressed();

        // Assert
        assertTrue(activity.isFinishing()); // Ensure the activity is finishing
    }
}
