package com.example.autismproject.Adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.autismproject.Models.Child;
import com.example.autismproject.Models.Task;
import com.example.autismproject.Models.Video;
import com.example.autismproject.R;
import com.example.autismproject.VideoPlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// this class is going to be in use to display child's to respective parents
public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.MyHolder>  {

    Context context;
    List<Video> videoList;
    // if true then give access to parent as deleting videos.
    // else give child access like opening the video
    Boolean isParent;

    public AdapterVideos(Context context, List<Video> videoList, Boolean isParent) {
        this.context = context;
        this.videoList = videoList;
        this.isParent = isParent;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_videos,parent,false);
        return new AdapterVideos.MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String cId= videoList.get(position).getcID();
        final String pId= videoList.get(position).getpID();
        final String vId= videoList.get(position).getvID();
        final String title= videoList.get(position).getTitle();
        final String url= videoList.get(position).getUrl();

        //setdata
        holder.title.setText(title);
        String videoID = extractYTId(url);
        String videoImgURl = "https://img.youtube.com/vi/" + videoID + "/0.jpg";
        Glide.with(context).load(videoImgURl).into(holder.videoThumbnail);

        holder.itemView.setOnClickListener(view -> {
            if (isParent) {
                showDeleteVideoDialog(videoList.get(position));
            } else {
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("selectedVideoID", videoID);
                editor.apply();
                context.startActivity(new Intent(context, VideoPlayer.class));
            }
        });

        // testing purpose
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("selectedVideoID", videoID);
                editor.apply();
                context.startActivity(new Intent(context, VideoPlayer.class));
                return false;
            }
        });
    }

    private void showDeleteVideoDialog(Video video) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    deleteVideo(video);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete Video?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void deleteVideo(Video video) {
        final ProgressDialog pd=new ProgressDialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
        pd.setMessage("Deleting..");
        Query fquery = FirebaseDatabase.getInstance().getReference("Videos").orderByChild("vID").equalTo(video.getvID());
        fquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
                Toast.makeText(context, "Deleted Video", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
        
        else {
            return String.format("%02d:%02d" , minute, second);
        }
    }

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView title, description, time;
        ImageView videoThumbnail;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.row_video_title);
//            description = itemView.findViewById(R.id.row_video_description);
            time = itemView.findViewById(R.id.row_video_time);
            videoThumbnail = itemView.findViewById(R.id.row_video_thumbnail);
        }
    }

}