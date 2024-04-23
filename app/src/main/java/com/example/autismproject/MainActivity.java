package com.example.autismproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.autismproject.Child.ChildLogin;
import com.example.autismproject.Parent.ParentLogin;
import com.example.autismproject.Parent.ParentRegister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button childLogin, parentLogin;

        childLogin = findViewById(R.id.home_child_login);
        parentLogin = findViewById(R.id.home_parent_login);

        childLogin.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ChildLogin.class)));

        parentLogin.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ParentRegister.class)));

    }
}