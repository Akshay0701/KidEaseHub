package com.example.autismproject;

import static org.mockito.Mockito.*;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;

import androidx.test.core.app.ApplicationProvider;

import com.example.autismproject.Parent.ParentLogin;
import com.example.autismproject.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class ParentLoginTest {

    private ParentLogin activity;
    private EditText parentEmail, parentPassword;
    private Button loginBtn, gotoRegister;
    private ImageView backBtn;

    @Mock
    private FirebaseAuth mockAuth;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Creating and setting up the activity
        ActivityController<ParentLogin> controller = Robolectric.buildActivity(ParentLogin.class).create().start();
        activity = controller.get();
        activity.setFirebaseAuth(mockAuth); // Injecting the mock FirebaseAuth

        // Initialize UI components
        parentEmail = activity.findViewById(R.id.parent_login_email);
        parentPassword = activity.findViewById(R.id.parent_login_password);
        loginBtn = activity.findViewById(R.id.parent_loginBtn);
        gotoRegister = activity.findViewById(R.id.gotoRegister);
        backBtn = activity.findViewById(R.id.backBtn);
    }

    @Test
    public void testLoginSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        when(parentEmail.getText().toString()).thenReturn(email);
        when(parentPassword.getText().toString()).thenReturn(password);
        Task<AuthResult> mockTask = mock(Task.class);
        when(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask);

        // Act
        loginBtn.performClick();

        // Assert
        verify(mockAuth).signInWithEmailAndPassword(email, password);
    }

    @Test
    public void testInvalidEmail() {
        // Arrange
        String email = "not_an_email";
        String password = "password123";

        when(parentEmail.getText().toString()).thenReturn(email);
        when(parentPassword.getText().toString()).thenReturn(password);

        // Act
        loginBtn.performClick();

        // Assert
        verify(mockAuth, never()).signInWithEmailAndPassword(anyString(), anyString());
    }

    @Test
    public void testPasswordTooShort() {
        // Arrange
        String email = "test@example.com";
        String password = "123";

        when(parentEmail.getText().toString()).thenReturn(email);
        when(parentPassword.getText().toString()).thenReturn(password);

        // Act
        loginBtn.performClick();

        // Assert
        verify(mockAuth, never()).signInWithEmailAndPassword(anyString(), anyString());
    }
}
