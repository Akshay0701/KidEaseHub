package com.example.autismproject.Adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismproject.Models.Category;
import com.example.autismproject.Models.Item;
import com.example.autismproject.Models.Video;
import com.example.autismproject.Parent.AddNewItem;
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

import java.util.List;

// this class is going to be in use to display child's to respective parents
public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyHolder>  {

    Context context;
    List<Category> categoryList;
    // if true then give access to parent as deleting videos.
    // else give child access like opening the video
    Boolean isParent;

    public AdapterCategory(Context context, List<Category> categoryList, Boolean isParent) {
        this.context = context;
        this.categoryList = categoryList;
        this.isParent = isParent;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_clickboard_category,parent,false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String cId= categoryList.get(position).getcID();
        final String pId= categoryList.get(position).getpID();
        final String url= categoryList.get(position).getImgUrl();
        int i = position;

        //setdata
        try{
            Picasso.get().load(url).placeholder(R.drawable.childlogo).into(holder.categoryImageView);
        }catch (Exception e){
            Picasso.get().load(R.drawable.childlogo).into(holder.categoryImageView);
        }

        holder.itemView.setOnClickListener(view -> {
            SharedPreferences.Editor editor;
            editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString("selectedCategoryID", cId);
            editor.apply();
        });

        holder.itemView.setOnLongClickListener(view -> {
            SharedPreferences.Editor editor;
            editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString("selectedCategoryID", cId);
            editor.apply();
            if (categoryList.get(i).getpID().equals("Admin")) {
                Toast.makeText(context, "default categories can't be deleted", Toast.LENGTH_SHORT).show();
            } else if(isParent) {
                showDeleteCategoryDialog(categoryList.get(i));
            }
            return false;
        });
    }

    void deleteItemsUnderCategoryId(String categoryID) {
        // go through all data of items associated with categoryID and delete them
        // load the items from respective category
        FirebaseDatabase.getInstance().getReference("Items").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Item item = ds.getValue(Item.class);
                    if(item != null && item.getcID().equals(categoryID))
                        deleteItem(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


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
                    pd.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    private void showDeleteCategoryDialog(Category category) {

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    context.startActivity(new Intent(context, AddNewItem.class));
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    deleteCategory(category);
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Select the action?").setPositiveButton("Create Item", dialogClickListener)
                .setNegativeButton("Delete Category", dialogClickListener).show();
    }

    // on long click delete the category with same pID
    private void deleteCategory(Category category) {
        final ProgressDialog pd=new ProgressDialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
        pd.setMessage("Deleting..");

        //image
        try {
            StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(category.getImgUrl());
            picRef.delete().addOnSuccessListener(aVoid -> {
                Query fquery = FirebaseDatabase.getInstance().getReference("Categories").orderByChild("cID").equalTo(category.getcID());
                fquery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        deleteItemsUnderCategoryId(category.getcID());
                        pd.dismiss();
                        Toast.makeText(context, "deleted category and all items", Toast.LENGTH_SHORT).show();
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
            Query fquery= FirebaseDatabase.getInstance().getReference("Categories").orderByChild("cID").equalTo(category.getcID());
            fquery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        ds.getRef().removeValue();
                    }
                    deleteItemsUnderCategoryId(category.getcID());
                    pd.dismiss();
                    Toast.makeText(context, "deleted category and all items", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    // set shared pref as delete the items with specific category id

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView categoryImageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.category_img);
        }
    }

}