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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.User;
import com.example.entregaaplicacionesmoviles.util.UtilDomi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirstLoginActivity extends AppCompatActivity {
    public static final int GALLERY_CALLBACK = 1;

    private ImageView photoIv2;
    private Button loadPhotoBtn2;
    private TextView delPhotoTxt2;
    private EditText descriptionEt2;
    private Button continueBtn;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        photoIv2 = findViewById(R.id.photoIv2);
        loadPhotoBtn2 = findViewById(R.id.loadPhotoBtn2);
        delPhotoTxt2 = findViewById(R.id.delPhotoTxt2);
        descriptionEt2 = findViewById(R.id.descriptionEt2);
        continueBtn = findViewById(R.id.continueBtn);

        delPhotoTxt2.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        },13);

        delPhotoTxt2.setOnClickListener(this::clickListener);
        loadPhotoBtn2.setOnClickListener(this::clickListener);
        continueBtn.setOnClickListener(this::clickListener);


    }

    private void clickListener(View view) {
        switch (view.getId()){
            case R.id.delPhotoTxt2:
                photoIv2.setImageBitmap(null);
                delPhotoTxt2.setVisibility(View.INVISIBLE);
                break;
            case R.id.loadPhotoBtn2:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_CALLBACK);
                break;
            case R.id.continueBtn:
                if(!descriptionEt2.getText().toString().isEmpty() && path != null){
                    User user = new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getDisplayName(), auth.getCurrentUser().getEmail(), descriptionEt2.getText().toString());
                    db.collection("users").document(user.getId()).set(user).addOnCompleteListener( task1 -> {
                        if(task1.isSuccessful()){
                            saveUserPhoto();
                            toRecommendActivity();
                        }else {
                            Toast.makeText(this,task1.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(this,"Pon una descripción y sube una foto",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void saveUserPhoto() {
        if(path!=null){
            try {
                FileInputStream fis = new FileInputStream(new File(path));
                storage.getReference().child("profiles").child("photo").child(auth.getCurrentUser().getUid()).putStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void toRecommendActivity(){
        Intent intent = new Intent(this, RecommendActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CALLBACK && resultCode== RESULT_OK){
            Uri photoUri = data.getData();
            path = UtilDomi.getPath(this,photoUri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            photoIv2.setImageBitmap(bitmap);
            delPhotoTxt2.setVisibility(View.VISIBLE);
        }
    }
}