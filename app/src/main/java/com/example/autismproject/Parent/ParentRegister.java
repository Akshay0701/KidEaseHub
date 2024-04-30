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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ParentRegister extends AppCompatActivity {

    public EditText parentName;
    public EditText parentEmail;
    public EditText parentPassword;
    public EditText parentPhoneno;

    public FirebaseAuth mAuth;

    Button gotoLogin,registerBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);

        parentEmail = findViewById(R.id.parent_register_email);
        parentName = findViewById(R.id.parent_register_name);
        parentPassword = findViewById(R.id.parent_register_password);
        parentPhoneno = findViewById(R.id.parent_register_phoneno);

        registerBtn = findViewById(R.id.parent_register_Btn);
        gotoLogin = findViewById(R.id.parent_register_gotoLogin);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        //init firebase
        mAuth = FirebaseAuth.getInstance();

        gotoLogin.setOnClickListener(view -> startActivity(new Intent(ParentRegister.this, ParentLogin.class)));

        registerBtn.setOnClickListener(view -> {
            //parent info
            String name,email,password,phone;
            email= parentEmail.getText().toString();
            name= parentName.getText().toString();
            password= parentPassword.getText().toString();
            phone= parentPhoneno.getText().toString();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                parentEmail.setError("Invalided Email");
                parentEmail.setFocusable(true);
            }
            else if(password.length()<6){
                parentPassword.setError("Password length at least 6 characters");
                parentPassword.setFocusable(true);
            }
            else if(name.isEmpty()){
                parentName.setError("Name is empty");
                parentName.setFocusable(true);
            }
            else if(phone.length()<10){
                parentPhoneno.setError("PhoneNo length at least 10 characters");
                parentPhoneno.setFocusable(true);
            }
            else {
                SharedPreferences.Editor editor;
                editor= PreferenceManager.getDefaultSharedPreferences(ParentRegister.this).edit();
                editor.putString("parentUsername", email.trim());
                editor.putString("password", password.trim());
                editor.apply();

                registerUser(name,email,phone,password);
            }
        });
    }

    private void registerUser(final String name, String email, final String phone, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        String email1 = user.getEmail();
                        String uid=user.getUid();
                        final HashMap<Object,String> hashMap=new HashMap<>();

                        //check if commander is allocated or  not
                        hashMap.put("name",name);
                        hashMap.put("email", email1);
                        hashMap.put("mobile",phone);
                        hashMap.put("password",password);
                        hashMap.put("pID",uid);
                        final FirebaseDatabase database=FirebaseDatabase.getInstance();

                        DatabaseReference reference=database.getReference("Parents");

                        reference.child(uid).setValue(hashMap);

                        //sucess
                        Toast.makeText(ParentRegister.this, "Registered with "+user.getEmail(), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ParentRegister.this, ParentHome.class));
                        finish();

                    }
                    else {
                        //progressDialog.dismiss();
                        Toast.makeText(ParentRegister.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(e -> Toast.makeText(ParentRegister.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            startActivity(new Intent(ParentRegister.this, ParentHome.class));
            finish();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String username=prefs.getString("parentUsername","");
        String pass=prefs.getString("password","");

        if(username.equals("")&&pass.equals("")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(username, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ParentRegister.this, ParentHome.class));
                            finish();

                        } else {
                            Toast.makeText(ParentRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(e -> Toast.makeText(ParentRegister.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());

        }
        super.onStart();
    }


    public boolean onSupportNavigateUp(){
        onBackPressed();//go baack
        return super.onSupportNavigateUp();
    }

}