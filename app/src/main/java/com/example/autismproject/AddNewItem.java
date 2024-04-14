package com.example.autismproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismproject.Models.Category;
import com.example.autismproject.Parent.ParentHome;
import com.example.autismproject.Parent.ParentRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AddNewItem extends AppCompatActivity {

    EditText itemName;
    Button addItem;
    ImageView imageIv, selectedCategoryImage;
    TextView selectedCategoryName;

    ImageView backBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //image picked will be saved in this
    Uri image_rui=null;

    String selectedCategoryID;
    Category selectedCategory;

    //permission constants
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;


    //permission constants
    private static final int IMAGE_PICK_CAMERA_CODE =300;
    private static final int IMAGE_PICK_GALLERY_CODE=400;

    //permission array
    String[] cameraPermessions;
    String[] storagePermessions;

    //progresses bar
    ProgressDialog pd;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            finish();
        });

        selectedCategoryImage = findViewById(R.id.add_item_selectedCategory);
        selectedCategoryName = findViewById(R.id.add_item_selectedCategory_text);

        itemName=findViewById(R.id.add_item_name);
        imageIv=findViewById(R.id.add_item_image);

        pd= new ProgressDialog(this);

        //init permissions
        cameraPermessions=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermessions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //image
        imageIv.setOnClickListener(v -> showImageDialog());

        addItem = findViewById(R.id.parent_add_itemBtn);
        addItem.setOnClickListener(view -> checkValidation());
    }

    private void checkValidation() {
        String name;
        name = itemName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        } else if(selectedCategory == null || selectedCategoryID.isEmpty()) {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        } else if(image_rui==null) {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }
        else{
            startAddingItem(name);
        }
    }

    private void startAddingItem(String name) {
        // TODO same as category need to look into it
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid = mAuth.getUid();
            mEmail = mAuth.getCurrentUser().getEmail();
        }else{
            startActivity(new Intent(AddNewItem.this, ParentRegister.class));
            finish();
        }

        // get selected child id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        selectedCategoryID = prefs.getString("selectedCategoryID","");

        if(selectedCategoryID.isEmpty()) {
            startActivity(new Intent(AddNewItem.this, ParentHome.class));
            finish();
        } else {
            // load category info
            FirebaseDatabase.getInstance().getReference("Categories").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Category category = ds.getValue(Category.class);
                        if(category != null && category.getcID().equals(selectedCategoryID)) {
                            selectedCategory = category;
                            //adapter
                            //setdata
                            try{
                                Picasso.get().load(category.getImgUrl()).placeholder(R.drawable.childlogo).into(selectedCategoryImage);
                            }catch (Exception e){
                                Picasso.get().load(R.drawable.childlogo).into(selectedCategoryImage);
                            }
                            selectedCategoryName.setText(category.getName());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AddNewItem.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



    private void showImageDialog() {


        String[] options={"Camera","Gallery"};

        //dialog box
        AlertDialog.Builder builder=new AlertDialog.Builder(AddNewItem.this);

        builder.setTitle("Choose Action");



        Toast.makeText(this, " reached", Toast.LENGTH_SHORT).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    //camera clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if(which==1){
                    //camera clicked

                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromCamera() {

        ContentValues cv=new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_rui=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermessions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result&&result1;
    }


    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermessions,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{

                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&&storageAccepted){

                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "camera  & gallery both permission needed", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    // sojao beta
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>1){
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_CODE){
                image_rui=data.getData();

                imageIv.setImageURI(image_rui);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE){

                imageIv.setImageURI(image_rui);

            }
        }
    }

}