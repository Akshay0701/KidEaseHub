package com.example.autismproject.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Models.Item;
import com.example.autismproject.Models.Video;
import com.example.autismproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

// this class is going to be in use to display child's to respective parents
public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyHolder>  {

    Context context;
    List<Item> itemList;
    // if true then give access to parent as deleting videos.
    // else give child access like opening the video
    Boolean isParent;

    public AdapterItem(Context context, List<Item> itemList, Boolean isParent) {
        this.context = context;
        this.itemList = itemList;
        this.isParent = isParent;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_clickboard_object,parent,false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String cId= itemList.get(position).getcID();
        final String pId= itemList.get(position).getpID();
        final String iId= itemList.get(position).getiID();
        final String text= itemList.get(position).getText();
        final String url= itemList.get(position).getImgUrl();

        //setdata
        try{
            Picasso.get().load(url).placeholder(R.drawable.childlogo).into(holder.itemImageView);
        }catch (Exception e){
            Picasso.get().load(R.drawable.childlogo).into(holder.itemImageView);
        }

        holder.itemText.setText(text);

        holder.itemView.setOnClickListener(view -> {
            if (isParent) {
                if (itemList.get(position).getpID().equals("Admin")) {
                    Toast.makeText(context, "default items can't be deleted", Toast.LENGTH_SHORT).show();
                } else {
                    showDeleteItemDialog(itemList.get(position));
                }
            } else {
                // for child click
                SharedPreferences.Editor editor;
                editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("selectedItemID", iId);
                editor.apply();
            }
        });
    }

    private void showDeleteItemDialog(Item item) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    deleteItem(item);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to this item?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    // on long click delete the category with same pID
    private void deleteItem(Item item) {
        final ProgressDialog pd=new ProgressDialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
        pd.setMessage("Deleting..");

        //image
        try {
            StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.getImgUrl());
            picRef.delete().addOnSuccessListener(aVoid -> {
                Query fquery = FirebaseDatabase.getInstance().getReference("Items").orderByChild("iID").equalTo(item.getiID());
                fquery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(context, "Deleted Item", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }).addOnFailureListener(e -> {
                pd.dismiss();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } catch (IllegalArgumentException e){
            Query fquery= FirebaseDatabase.getInstance().getReference("Items").orderByChild("iID").equalTo(item.getiID());
            fquery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        ds.getRef().removeValue();
                    }
                    Toast.makeText(context, "Deleted Item", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView itemText;
        ImageView itemImageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.name);
            itemImageView = itemView.findViewById(R.id.img);
        }
    }

}