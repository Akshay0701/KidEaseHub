package com.example.autismproject.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.autismproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ParentLogin extends AppCompatActivity {

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.mAuth = firebaseAuth;
    }

    EditText parentEmail, parentPassword;

    private FirebaseAuth mAuth;

    Button gotoRegister, loginBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        parentEmail = findViewById(R.id.parent_login_email);
        parentPassword = findViewById(R.id.parent_login_password);
        gotoRegister = findViewById(R.id.gotoRegister);
        loginBtn = findViewById(R.id.parent_loginBtn);

        //init firebase
        mAuth = FirebaseAuth.getInstance();

        gotoRegister.setOnClickListener(view -> startActivity(new Intent(ParentLogin.this, ParentRegister.class)));

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email= parentEmail.getText().toString();
                password= parentPassword.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    parentEmail.setError("Invalided Email");
                    parentEmail.setFocusable(true);
                } else if (password.length()<6) {
                    parentPassword.setError("Password length at least 6 characters");
                    parentPassword.setFocusable(true);
                } else {
                    loginParent(email,password);
                }
            }
        });

    }

    private void loginParent(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                SharedPreferences.Editor editor;
                editor= PreferenceManager.getDefaultSharedPreferences(ParentLogin.this).edit();
                editor.putString("parentUsername", email.trim());
                editor.putString("password", password.trim());
                editor.apply();
                Toast.makeText(ParentLogin.this, "Logged In: " + email, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ParentLogin.this, ParentHome.class));
                finish();
            }
        });
    }
}