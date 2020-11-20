package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText loginEmailEt;
    private EditText loginPasswordEt;
    private Button loginBtn;
    private Button loginGoogleBtn;
    private Button loginFacebookBtn;
    private Button registrationEmailBtn;
    private Button registrationGoogleBtn;
    private Button registrationFacebookBtn;
    private TextView recoverPasswordTxt;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEt = findViewById(R.id.loginEmailEt);
        loginPasswordEt = findViewById(R.id.loginPasswordEt);
        loginBtn = findViewById(R.id.loginBtn);
        loginGoogleBtn = findViewById(R.id.loginGoogleBtn);
        loginFacebookBtn = findViewById(R.id.loginFacebookBtn);
        registrationEmailBtn = findViewById(R.id.registrationEmailBtn);
        registrationGoogleBtn = findViewById(R.id.registrationGoogleBtn);;
        registrationFacebookBtn = findViewById(R.id.registrationFacebookBtn);;
        recoverPasswordTxt = findViewById(R.id.recoverPasswordTxt);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(this);
        loginGoogleBtn.setOnClickListener(this);
        loginFacebookBtn.setOnClickListener(this);
        registrationEmailBtn.setOnClickListener(this);
        registrationGoogleBtn.setOnClickListener(this);
        registrationFacebookBtn.setOnClickListener(this);
        recoverPasswordTxt.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                if(!loginEmailEt.getText().toString().isEmpty() && !loginPasswordEt.getText().toString().isEmpty()  ){
                    auth.signInWithEmailAndPassword(loginEmailEt.getText().toString(),loginPasswordEt.getText().toString()).addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    String uid = task.getResult().getUser().getUid();
                                    if(task.getResult().getUser().isEmailVerified()){
                                        Intent i = new Intent(this, HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(this,"Verifica primero tu Email",Toast.LENGTH_LONG).show();
                                        auth.signOut();
                                    }
                                }else {
                                    Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                }else{
                    Toast.makeText(this,"Ingresa tu correo y contraseña",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.loginGoogleBtn:
                break;
            case R.id.loginFacebookBtn:
                break;
            case R.id.registrationEmailBtn:
                Intent emailRegistration = new Intent(this,RegistrationEmailActivity.class);
                startActivity(emailRegistration);
                break;
            case R.id.registrationGoogleBtn:
                break;
            case R.id.registrationFacebookBtn:
                break;
        }
    }
}