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
import android.util.Patterns;
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
import java.util.UUID;
import java.util.regex.Pattern;

public class RegistrationEmailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int GALLERY_CALLBACK = 1;

    private ImageView photoIv;
    private Button loadPhotoBtn;
    private TextView delPhotoTxt;
    private EditText nameEt;
    private EditText emailEt;
    private EditText passwordEt;
    private EditText rePasswordEt;
    private Button registerBtn;
    private TextView singinTxt;

    private String path;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);
        photoIv = findViewById(R.id.photoIv);
        loadPhotoBtn = findViewById(R.id.loadPhotoBtn);
        delPhotoTxt = findViewById(R.id.delPhotoTxt);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        rePasswordEt = findViewById(R.id.rePasswordEt);
        registerBtn = findViewById(R.id.registerBtn);
        singinTxt = findViewById(R.id.singinTxt);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        delPhotoTxt.setVisibility(View.INVISIBLE);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        },13);

        loadPhotoBtn.setOnClickListener(this);
        delPhotoTxt.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        singinTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loadPhotoBtn:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_CALLBACK);
                break;
            case R.id.delPhotoTxt:
                photoIv.setImageBitmap(null);
                delPhotoTxt.setVisibility(View.INVISIBLE);
                break;
            case R.id.registerBtn:
                if(validateRegister()){
                    auth.createUserWithEmailAndPassword(emailEt.getText().toString(),passwordEt.getText().toString()).addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    User user = new User(task.getResult().getUser().getUid(), nameEt.getText().toString(), emailEt.getText().toString());
                                    db.collection("users").document(user.getId()).set(user).addOnCompleteListener( task1 -> {
                                        if(task1.isSuccessful()){
                                            saveUserPhoto();
                                            sendVerification();
                                            toLoginActivity();
                                        }
                                    });
                                }else {
                                    Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                }else{
                    Toast.makeText(this,"Verifica el nombre, correo o contraseña",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.singinTxt:
                finish();
                break;
        }
    }

    public boolean validateRegister(){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean validName = nameEt.getText().toString().isEmpty();
        boolean validPassword = passwordEt.getText().toString().equals(rePasswordEt.getText().toString()) && passwordEt.getText().toString().length()>=6;
        boolean validEmail = pattern.matcher(emailEt.getText().toString()).matches();
        return validEmail && validPassword && !validName;

    }
    private void saveUserPhoto(){
        if(path!=null){
            try {
                FileInputStream fis = new FileInputStream(new File(path));
                storage.getReference().child("profiles").child("photo").child(auth.getCurrentUser().getUid()).putStream(fis);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    private void sendVerification() {
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this,"Email de verificación enviado, revisa la bandeja de entrada",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
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
            photoIv.setImageBitmap(bitmap);
            delPhotoTxt.setVisibility(View.VISIBLE);
        }
    }
}