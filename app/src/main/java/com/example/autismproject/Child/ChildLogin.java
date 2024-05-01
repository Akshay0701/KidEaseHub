package com.example.autismproject.Child;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterChilds;
import com.example.autismproject.MainActivity;
import com.example.autismproject.Models.Child;
import com.example.autismproject.Parent.ParentHome;
import com.example.autismproject.Parent.ParentLogin;
import com.example.autismproject.Parent.ParentRegister;
import com.example.autismproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// no need for login for child login
public class ChildLogin extends AppCompatActivity {

    EditText parentEmail, parentPassword;

    public FirebaseAuth mAuth;

    Button gotoRegister, loginBtn;

    RecyclerView childrenRecyclerView;
    RecyclerView.LayoutManager  layoutManager;
    List<Child> childList;
    AdapterChilds adapterChilds;

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    LinearLayout parentLinear, childSelectionLinear;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);

        parentEmail = findViewById(R.id.child_login_parentEmail);
        parentPassword = findViewById(R.id.child_login_parentPassword);
        gotoRegister = findViewById(R.id.gotoRegister);
        loginBtn = findViewById(R.id.child_login_signIn);
        parentLinear = findViewById(R.id.child_login_parentLinear);
        childSelectionLinear = findViewById(R.id.child_login_childselectionLinear);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Childs");

        gotoRegister.setOnClickListener(view -> {
            Toast.makeText(this, "Only Parent can register new Child", Toast.LENGTH_SHORT).show();
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


        // load recycleBook
        childrenRecyclerView =(RecyclerView)findViewById(R.id.parent_home_childrecyclerView);
        childrenRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ChildLogin.this);
        childrenRecyclerView.setLayoutManager(layoutManager);

        childList = new ArrayList<>();
        
    }

    private void loadChilds() {
        String mUid,mEmail;
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid = mAuth.getUid();
            mEmail = mAuth.getCurrentUser().getEmail();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    childList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Child child = ds.getValue(Child.class);
                        if(child != null && child.getpID().equals(mUid))
                            childList.add(child);
                    }

                    //adapter
                    adapterChilds = new AdapterChilds(ChildLogin.this, childList, false);
                    childrenRecyclerView.setLayoutManager(new LinearLayoutManager(ChildLogin.this, LinearLayoutManager.VERTICAL, false));

                    //set adapter to recycle
                    childrenRecyclerView.setAdapter(adapterChilds);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChildLogin.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            startActivity(new Intent(ChildLogin.this, MainActivity.class));
            finish();
        }
    }

    public void loginParent(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            // make email password invisible and child selection as visible
            parentLinear.setVisibility(View.GONE);
            childSelectionLinear.setVisibility(View.VISIBLE);
            // then load the childs for selections in recyclerview
            loadChilds();
            // lastly save the parent details for further used
            SharedPreferences.Editor editor;
            editor= PreferenceManager.getDefaultSharedPreferences(ChildLogin.this).edit();
            editor.putString("parentUsername", email.trim());
            editor.putString("password", password.trim());
            editor.apply();
        });
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String username=prefs.getString("parentUsername","");
        String selectedChild=prefs.getString("selectedChildID","");
        String pass=prefs.getString("password","");

        if(username.equals("")&& pass.equals("") || selectedChild.equals("")) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(username, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            // todo call child home page
                            finish();

                        } else {
                            Toast.makeText(ChildLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(e -> Toast.makeText(ChildLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());

        }
        super.onStart();
    }


    public boolean onSupportNavigateUp(){
        onBackPressed();//go baack
        return super.onSupportNavigateUp();
    }

}