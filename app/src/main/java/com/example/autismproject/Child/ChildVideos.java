package com.example.autismproject.Child;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterVideos;
import com.example.autismproject.Models.Video;
import com.example.autismproject.Parent.ParentHome;
import com.example.autismproject.Parent.ParentRegister;
import com.example.autismproject.Parent.ParentVideo;
import com.example.autismproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChildVideos extends AppCompatActivity {

    RecyclerView videoRecyclerView;
    RecyclerView.LayoutManager  layoutManager;
    List<Video> videoList;
    AdapterVideos adapterVideos;
    ImageView backBtn;

    // child id for retrieving all tasks
    String cID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_videos);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Videos");

        //load recycleBook
        videoRecyclerView =(RecyclerView)findViewById(R.id.child_video_recyclerView);
        videoRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ChildVideos.this);
        videoRecyclerView.setLayoutManager(layoutManager);

        videoList = new ArrayList<>();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });
    }


    void loadVideos() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Video video = ds.getValue(Video.class);
                    if(video != null && video.getcID().equals(cID))
                        videoList.add(video);
                }

                //adapter
                adapterVideos = new AdapterVideos(ChildVideos.this, videoList, false);
                videoRecyclerView.setLayoutManager(new LinearLayoutManager(ChildVideos.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                videoRecyclerView.setAdapter(adapterVideos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChildVideos.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(ChildVideos.this, ChildLogin.class));
            finish();
        }

        // get selected child id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        cID=prefs.getString("selectedChildID","");

        if(cID.isEmpty()) {
            startActivity(new Intent(ChildVideos.this, ChildHome.class));
            finish();
        } else {
            loadVideos();
        }
    }

}