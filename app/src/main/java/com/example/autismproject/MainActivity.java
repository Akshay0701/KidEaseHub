package com.example.autismproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.autismproject.Child.ChildLogin;
import com.example.autismproject.Parent.ParentLogin;
import com.example.autismproject.Parent.ParentRegister;

interface LoginHandler {
    void handleLogin();
}

class ChildLoginHandler implements LoginHandler {
    private Context mContext;

    ChildLoginHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handleLogin() {
        Intent intent = new Intent(mContext, ChildLogin.class);
        mContext.startActivity(intent);
    }
}

class ParentLoginHandler implements LoginHandler {
    private Context mContext;

    ParentLoginHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handleLogin() {
        Intent intent = new Intent(mContext, ParentRegister.class);
        mContext.startActivity(intent);
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button childLogin, parentLogin;

        childLogin = findViewById(R.id.home_child_login);
        parentLogin = findViewById(R.id.home_parent_login);

        final LoginHandler childLoginHandler = new ChildLoginHandler(MainActivity.this);
        final LoginHandler parentLoginHandler = new ParentLoginHandler(MainActivity.this);

        childLogin.setOnClickListener(view -> childLoginHandler.handleLogin());

        parentLogin.setOnClickListener(view -> parentLoginHandler.handleLogin());
    }
}
