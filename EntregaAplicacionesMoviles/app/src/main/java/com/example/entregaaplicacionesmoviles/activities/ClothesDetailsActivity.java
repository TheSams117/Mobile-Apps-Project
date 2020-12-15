package com.example.entregaaplicacionesmoviles.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.util.Util;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.util.UtilDomi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;

public class ClothesDetailsActivity extends AppCompatActivity {
    private ImageView imageClothes;
    private Button addPhotos, publishClothes;
    private EditText detailsClothes, priceClothes, nameClothes;
    private final static int GALLERY_CALLBACK = 1;
    private String path;
    private FirebaseStorage storage;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_details);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, 1);

        imageClothes = findViewById(R.id.clothesImg);
        addPhotos = findViewById(R.id.addPhotoBtn);
        publishClothes = findViewById(R.id.publishClothesBtn);
        nameClothes = findViewById(R.id.nameClothesEdt);
        detailsClothes = findViewById(R.id.detailsClothesEdt);
        priceClothes = findViewById(R.id.priceClothesEdt);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        addPhotos.setOnClickListener(
                v -> {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    startActivityForResult(i, GALLERY_CALLBACK);
                }
        );

        publishClothes.setOnClickListener(
                v -> {
                    if (path == null) return;
                    try {
                        String idImage = UUID.randomUUID().toString();
                        String id = UUID.randomUUID().toString();
                        String price = priceClothes.getText().toString();
                        String details = detailsClothes.getText().toString();
                        String name = nameClothes.getText().toString();

                        HashMap<String,String> product = new HashMap<>();
                        product.put("name",name);
                        product.put("price",price);
                        product.put("details",details);
                        product.put("photo",idImage);
                        Log.e(">>>",idImage);

                        FileInputStream fis = new FileInputStream(new File(path));
                        storage.getReference().child("clothes").child(idImage).putStream(fis).addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        db.collection("users")
                                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .collection("products")
                                                .document(id).set(product);
                                    }
                                }
                        );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    toPerfilActivity();
                }
        );

    }

    private void toPerfilActivity() {
        runOnUiThread(
                ()->{
                    Intent i = new Intent(this, PerfilActivity.class);
                    startActivity(i);
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CALLBACK && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            path = UtilDomi.getPath(this, photoUri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageClothes.setImageBitmap(bitmap);
        }
    }
}