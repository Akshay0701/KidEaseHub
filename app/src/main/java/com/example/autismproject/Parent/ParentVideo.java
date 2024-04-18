package com.example.autismproject.Parent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterVideos;
import com.example.autismproject.Models.Task;
import com.example.autismproject.Models.Video;
import com.example.autismproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ParentVideo extends AppCompatActivity {

    Button addNewVideo;

    RecyclerView videoRecyclerView;
    RecyclerView.LayoutManager  layoutManager;
    List<Video> videoList;
    AdapterVideos adapterVideos;

    // child id for retrieving all tasks
    String cID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_video);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Videos");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        addNewVideo = findViewById(R.id.parent_video_addnewvideo);
        addNewVideo.setOnClickListener(view -> {
            // open create new task activity
            createNewTaskDialog();
        });

        //load recycleBook
        videoRecyclerView =(RecyclerView)findViewById(R.id.parent_video_recyclerView);
        videoRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ParentVideo.this);
        videoRecyclerView.setLayoutManager(layoutManager);

        videoList = new ArrayList<>();
    }

    private void createNewTaskDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 20);

        final EditText nameBox = new EditText(this);
        nameBox.setHint("Video Title");
        nameBox.setTextColor(getResources().getColor(R.color.colorPrimaryblack));
        nameBox.setHintTextColor(getResources().getColor(R.color.colorPrimaryFadded));
        layout.addView(nameBox);

        final EditText urlBox = new EditText(this);
        urlBox.setHint("Video URL");
        urlBox.setTextColor(getResources().getColor(R.color.colorPrimaryblack));
        urlBox.setHintTextColor(getResources().getColor(R.color.colorPrimaryFadded));
        layout.addView(urlBox);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AlertDialog);
        alert.setIcon(R.drawable.childlogo).setTitle("Enter Video Details:").setView(layout).setPositiveButton("Save",
                (dialog, whichButton) -> {
                    // proceed with creating child
                    if(nameBox.getText().toString().isEmpty()) {
                        nameBox.setError("Title is empty");
                        nameBox.setFocusable(true);
                    }
                    else if(!isYoutubeUrl(urlBox.getText().toString())) {
                        urlBox.setError("url is empty");
                        urlBox.setFocusable(true);
                    }
                    else {
                        // upload data
                        String vID = databaseReference.push().getKey();
                        final HashMap<Object,String> hashMap=new HashMap<>();

                        //check if commander is allocated or  not
                        hashMap.put("title", nameBox.getText().toString());
                        hashMap.put("url", urlBox.getText().toString());
                        hashMap.put("vID", vID);
                        hashMap.put("cID", cID);
                        hashMap.put("pID",mUid);

                        databaseReference.child(vID).setValue(hashMap);

                        // success
                        Toast.makeText(ParentVideo.this, "Uploaded with "+ vID, Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel",
                (dialog, whichButton) -> {
                    /*
                     * User clicked cancel so do some stuff
                     */
                });
        alert.show();
    }

    public static boolean isYoutubeUrl(String youTubeURl)
    {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern))
        {
            success = true;
        }
        else
        {
            // Not Valid youtube URL
            success = false;
        }
        return success;
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
                adapterVideos = new AdapterVideos(ParentVideo.this, videoList, true);
                videoRecyclerView.setLayoutManager(new LinearLayoutManager(ParentVideo.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                videoRecyclerView.setAdapter(adapterVideos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ParentVideo.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(ParentVideo.this, ParentRegister.class));
            finish();
        }

        // get selected child id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        cID=prefs.getString("selectedChildID","");

        if(cID.isEmpty()) {
            startActivity(new Intent(ParentVideo.this, ParentHome.class));
            finish();
        } else {
            loadVideos();
        }
    }

}