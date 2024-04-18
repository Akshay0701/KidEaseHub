package com.example.autismproject.Child;

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
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismproject.Adapters.AdapterCategory;
import com.example.autismproject.Adapters.AdapterItem;
import com.example.autismproject.Models.Category;
import com.example.autismproject.Models.Item;
import com.example.autismproject.Parent.ParentRegister;
import com.example.autismproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChildClickBoard extends AppCompatActivity {


    // Selected Items
    ImageView selectedItemImage1, selectedItemImage2, selectedItemImage3, playIcon, deleteIcon;
    TextView selectedItemText1, selectedItemText2, selectedItemText3;
    ImageView backBtn;

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

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_click_board);

        selectedItemImage1 = findViewById(R.id.child_clickboard_selectedImg1);
        selectedItemImage2 = findViewById(R.id.child_clickboard_selectedImg2);
        selectedItemImage3 = findViewById(R.id.child_clickboard_selectedImg3);
        selectedItemText1 = findViewById(R.id.child_clickboard_selectedText1);
        selectedItemText2 = findViewById(R.id.child_clickboard_selectedText2);
        selectedItemText3 = findViewById(R.id.child_clickboard_selectedText3);
        playIcon = findViewById(R.id.child_clickboard_playBtn);
        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSelectedItems();
            }
        });

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        deleteIcon = findViewById(R.id.child_clickboard_deleteBtn);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelectedItems();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setSpeechRate((float)0.7);
                }
            }
        });

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCategory = firebaseDatabase.getReference("Categories");
        databaseReferenceItem = firebaseDatabase.getReference("Items");
        pref = PreferenceManager.getDefaultSharedPreferences(ChildClickBoard.this);

        //load category recyclerview
        categoryRecyclerView = findViewById(R.id.child_clickboard_category_recyclerview);
        categoryRecyclerView.setHasFixedSize(true);
        categorylayoutManager=new LinearLayoutManager(ChildClickBoard.this);
        categoryRecyclerView.setLayoutManager(categorylayoutManager);

        categoryList = new ArrayList<>();

        //load item recyclerview
        itemRecyclerView = findViewById(R.id.child_clickboard_item_recyclerview);
        itemRecyclerView.setHasFixedSize(true);
        itemlayoutManager=new GridLayoutManager(ChildClickBoard.this, 3);
        itemRecyclerView.setLayoutManager(itemlayoutManager);

        itemList = new ArrayList<>();

        listener = (prefs, key) -> {
            // load items again
            if (key.equals("selectedItemID")) {
                selectItemAndLoad(prefs.getString(key, ""));
            } else if (key.equals("selectedCategoryID")) {
                loadItems(prefs.getString(key, ""));
            }
        };

        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    private void selectItemAndLoad(String idStr) {
        if (!idStr.equals("")) {
            databaseReferenceItem.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Item item = dataSnapshot.child(idStr).getValue(Item.class);
                    if (selectedItemText1.getText().toString().equals("")) {
                        try{
                            Picasso.get().load(item.getImgUrl()).placeholder(R.drawable.childlogo).into(selectedItemImage1);
                        }catch (Exception e){
                            Picasso.get().load(R.drawable.circle).into(selectedItemImage1);
                        }
                        selectedItemText1.setText(item.getText());
                    } else if (selectedItemText2.getText().toString().equals("")) {
                        try{
                            Picasso.get().load(item.getImgUrl()).placeholder(R.drawable.childlogo).into(selectedItemImage2);
                        }catch (Exception e){
                            Picasso.get().load(R.drawable.circle).into(selectedItemImage2);
                        }
                        selectedItemText2.setText(item.getText());
                    } else {
                        try{
                            Picasso.get().load(item.getImgUrl()).placeholder(R.drawable.childlogo).into(selectedItemImage3);
                        }catch (Exception e){
                            Picasso.get().load(R.drawable.circle).into(selectedItemImage3);
                        }
                        selectedItemText3.setText(item.getText());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChildClickBoard.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                adapterCategory = new AdapterCategory(ChildClickBoard.this, categoryList, false);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(ChildClickBoard.this,
                        LinearLayoutManager.HORIZONTAL, false));

                //set adapter to recycle
                categoryRecyclerView.setAdapter(adapterCategory);

                if (!categoryList.isEmpty()) {
                    loadItems(categoryList.get(0).getcID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChildClickBoard.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                adapterItem = new AdapterItem(ChildClickBoard.this, itemList, false);
                //set adapter to recycle
                itemRecyclerView.setAdapter(adapterItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChildClickBoard.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(ChildClickBoard.this, ParentRegister.class));
            finish();
        }
        // load data
        loadCategory();


    }


    void playSelectedItems() {
        if (!textToSpeech.isSpeaking()) {
            if (selectedItemText1.getText().toString().equals("")) {
                textToSpeech.speak("Select the item",TextToSpeech.QUEUE_FLUSH,null);
            } else {
                String speakStr = selectedItemText1.getText().toString() + selectedItemText2.getText().toString()
                        + selectedItemText3.getText().toString();
                textToSpeech.speak(speakStr,TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }

    void clearSelectedItems() {
        selectedItemText1.setText("");
        selectedItemText2.setText("");
        selectedItemText3.setText("");

        Picasso.get().load(R.drawable.circle).into(selectedItemImage1);
        Picasso.get().load(R.drawable.circle).into(selectedItemImage2);
        Picasso.get().load(R.drawable.circle).into(selectedItemImage3);
    }
}