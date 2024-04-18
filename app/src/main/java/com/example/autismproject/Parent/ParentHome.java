package com.example.autismproject.Parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterChilds;
import com.example.autismproject.MainActivity;
import com.example.autismproject.Models.Child;
import com.example.autismproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParentHome extends AppCompatActivity {

    Button addNewChild, clickboard;
    ImageView logout;

    RecyclerView childrenRecyclerView;
    RecyclerView.LayoutManager  layoutManager;
    List<Child> childList;
    AdapterChilds adapterChilds;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        addNewChild = findViewById(R.id.parent_home_addnewchild);

        addNewChild.setOnClickListener(view -> {
            // make a dialog to create new child
            createNewChildDialog();
        });

        clickboard = findViewById(R.id.parent_home_Clickboard);

        clickboard.setOnClickListener(view -> {
            startActivity(new Intent(ParentHome.this, ParentClickBoard.class));
        });

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Childs");

        //load recycleBook
        childrenRecyclerView =(RecyclerView)findViewById(R.id.parent_home_childrecyclerView);
        childrenRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ParentHome.this);
        childrenRecyclerView.setLayoutManager(layoutManager);

        childList = new ArrayList<>();
        loadChilds();

        logout = findViewById(R.id.parent_home_logout);
        logout.setOnClickListener(view -> {
            showLogoutDialog();
        });
    }

    private void showLogoutDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        startLogout();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void startLogout() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ParentHome.this).edit();
        editor.remove("parentUsername");
        editor.remove("password");
        editor.apply();
        mAuth.signOut();
        startActivity(new Intent(ParentHome.this, MainActivity.class));
        Toast.makeText(this, "Logged out:" + mEmail, Toast.LENGTH_SHORT).show();
    }

    private void createNewChildDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 20);

        final EditText nameBox = new EditText(this);
        nameBox.setHint("Child Full Name");
        nameBox.setTextColor(getResources().getColor(R.color.colorPrimaryblack));
        nameBox.setHintTextColor(getResources().getColor(R.color.colorPrimaryFadded));
        layout.addView(nameBox);

        final EditText ageBox = new EditText(this);
        ageBox.setHint("Age");
        ageBox.setTextColor(getResources().getColor(R.color.colorPrimaryblack));
        ageBox.setHintTextColor(getResources().getColor(R.color.colorPrimaryFadded));
        layout.addView(ageBox);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AlertDialog);
        alert.setIcon(R.drawable.childlogo).setTitle("Enter Child Details:").setView(layout).setPositiveButton("Save",
                (dialog, whichButton) -> {
                    // proceed with creating child
                    if(nameBox.getText().toString().isEmpty()){
                        nameBox.setError("Name is empty");
                        nameBox.setFocusable(true);
                    }
                    else if(ageBox.getText().toString().isEmpty()){
                        ageBox.setError("Age is empty");
                        ageBox.setFocusable(true);
                    }
                    else {
                        // upload data
                        String cID = databaseReference.push().getKey();
                        final HashMap<Object,String> hashMap=new HashMap<>();

                        //check if commander is allocated or  not
                        hashMap.put("name",nameBox.getText().toString());
                        hashMap.put("age", ageBox.getText().toString());
                        hashMap.put("scores", "0");
                        hashMap.put("cID", cID);
                        hashMap.put("imgUrl", "https://www.familyeducation.com/sites/default/files/inline-images/teaching-kids-about-autism_repetitive-motions.jpg");
                        hashMap.put("pID",mUid);

                        databaseReference.child(cID).setValue(hashMap);

                        // success
                        Toast.makeText(ParentHome.this, "Registered with "+ cID, Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel",
                (dialog, whichButton) -> {
                    /*
                     * User clicked cancel so do some stuff
                     */
                });
        alert.show();
    }

    private void loadChilds() {
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
                adapterChilds = new AdapterChilds(ParentHome.this, childList, true);
                childrenRecyclerView.setLayoutManager(new LinearLayoutManager(ParentHome.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                childrenRecyclerView.setAdapter(adapterChilds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ParentHome.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid = mAuth.getUid();
            mEmail = mAuth.getCurrentUser().getEmail();
        }else{
            startActivity(new Intent(ParentHome.this, ParentRegister.class));
            finish();
        }
    }
}