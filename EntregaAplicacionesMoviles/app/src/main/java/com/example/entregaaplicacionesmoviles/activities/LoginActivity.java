package com.example.entregaaplicacionesmoviles.activities;

import androidx.annotation.NonNull;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

import bolts.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText loginEmailEt;
    private EditText loginPasswordEt;
    private Button loginBtn;
    private Button loginGoogleBtn;
    private Button loginFacebookBtn;
    private Button registrationEmailBtn;
    private TextView recoverPasswordTxt;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        loginEmailEt = findViewById(R.id.loginEmailEt);
        loginPasswordEt = findViewById(R.id.loginPasswordEt);
        loginBtn = findViewById(R.id.loginBtn);
        loginGoogleBtn = findViewById(R.id.loginGoogleBtn);
        loginFacebookBtn = findViewById(R.id.loginFacebookBtn);
        registrationEmailBtn = findViewById(R.id.registrationEmailBtn);
        recoverPasswordTxt = findViewById(R.id.recoverPasswordTxt);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(this);
        loginGoogleBtn.setOnClickListener(this);
        loginFacebookBtn.setOnClickListener(this);
        registrationEmailBtn.setOnClickListener(this);
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
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(">>>","Entra OS");
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Log.e(">>>","Entra OC");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e(">>>","Entra OE");
                        // App code
                    }
                });
                break;
            case R.id.registrationEmailBtn:
                Intent emailRegistration = new Intent(this,RegistrationEmailActivity.class);
                startActivity(emailRegistration);
                break;
        }
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Intent i = new Intent(this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}