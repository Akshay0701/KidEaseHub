package com.example.autismproject.Parent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterCategory;
import com.example.autismproject.Adapters.AdapterItem;
import com.example.autismproject.AddNewCategory;
import com.example.autismproject.AddNewItem;
import com.example.autismproject.Models.Category;
import com.example.autismproject.Models.Item;
import com.example.autismproject.Models.Task;
import com.example.autismproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentClickBoard extends AppCompatActivity {


    Button addNewCategory, addNewItem;

    // for category
    RecyclerView categoryRecyclerView;
    RecyclerView.LayoutManager  categorylayoutManager;
    List<Category> categoryList;
    AdapterCategory adapterCategory;

    SharedPreferences pref;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    // for items underneath the categories
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager  itemlayoutManager;
    List<Item> itemList;
    AdapterItem adapterItem;

    // child id for retrieving all tasks
    String cID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceCategory;
    DatabaseReference databaseReferenceItem;

    String mUid,mEmail;
    private FirebaseAuth mAuth;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_click_board);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCategory = firebaseDatabase.getReference("Categories");
        databaseReferenceItem = firebaseDatabase.getReference("Items");
        pref = PreferenceManager.getDefaultSharedPreferences(ParentClickBoard.this);

        addNewCategory = findViewById(R.id.parent_clickboard_addcategory);
        addNewCategory.setOnClickListener(view -> {
            startActivity(new Intent(ParentClickBoard.this, AddNewCategory.class));
        });

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        addNewItem = findViewById(R.id.parent_clickboard_additem);
        addNewItem.setOnClickListener(view -> {
            if (cID.isEmpty() && !categoryList.isEmpty()) {
                SharedPreferences.Editor editor;
                editor= PreferenceManager.getDefaultSharedPreferences(this).edit();
                editor.putString("selectedCategoryID", categoryList.get(0).getcID());
                editor.apply();
            }
            startActivity(new Intent(ParentClickBoard.this, AddNewItem.class));
        });

        //load category recyclerview
        categoryRecyclerView = findViewById(R.id.parent_clickboard_category_recyclerview);
        categoryRecyclerView.setHasFixedSize(true);
        categorylayoutManager=new LinearLayoutManager(ParentClickBoard.this);
        categoryRecyclerView.setLayoutManager(categorylayoutManager);

        categoryList = new ArrayList<>();

        //load item recyclerview
        itemRecyclerView = findViewById(R.id.parent_clickboard_item_recyclerview);
        itemRecyclerView.setHasFixedSize(true);
        itemlayoutManager=new GridLayoutManager(ParentClickBoard.this, 3);
        itemRecyclerView.setLayoutManager(itemlayoutManager);

        itemList = new ArrayList<>();

        listener = (prefs, key) -> {
            // load items again
            loadItems(prefs.getString(key, ""));
        };

        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    void loadCategory() {
        databaseReferenceCategory.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Category category = ds.getValue(Category.class);
                    if(category != null && (category.getpID().equals(mUid) || category.getpID().equals("Admin")))
                        categoryList.add(category);
                }

                //adapter
                adapterCategory = new AdapterCategory(ParentClickBoard.this, categoryList, true);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(ParentClickBoard.this, LinearLayoutManager.HORIZONTAL, false));

                //set adapter to recycle
                categoryRecyclerView.setAdapter(adapterCategory);

                if (!categoryList.isEmpty()) {
                    loadItems(categoryList.get(0).getcID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ParentClickBoard.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loadItems(String cID) {
        // load the items from respective category
        this.cID = cID;
        databaseReferenceItem.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Item item = ds.getValue(Item.class);
                    if(item != null && item.getcID().equals(cID) && (item.getpID().equals(mUid) || item.getpID().equals("Admin")))
                        itemList.add(item);
                }

                //adapter
                adapterItem = new AdapterItem(ParentClickBoard.this, itemList, true);
                //set adapter to recycle
                itemRecyclerView.setAdapter(adapterItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ParentClickBoard.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(ParentClickBoard.this, ParentRegister.class));
            finish();
        }
        // load data
        loadCategory();


    }

}