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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
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
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
                signInWithEmail();
                break;
            case R.id.loginGoogleBtn:
                signInWithGoolge();
                break;
            case R.id.loginFacebookBtn:
                signInWithFacebook();
                break;
            case R.id.registrationEmailBtn:
                Intent emailRegistration = new Intent(this,RegistrationEmailActivity.class);
                startActivity(emailRegistration);
                break;
            case R.id.recoverPasswordTxt:
                Intent recoverPass = new Intent(this,RecoverPasswordActivity.class);
                startActivity(recoverPass);
                break;
        }
    }

    private void isFirstLogin(){

        db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().getData() !=null){
                haveFollowing();
            }else {
                IntentToFirstLoginActivity();
            }
        });
    }

    private void haveFollowing(){

        db.collection("users").document(auth.getCurrentUser().getUid()).collection("following").get().addOnCompleteListener( task -> {
            if(task.isSuccessful()){
               if(task.getResult().size()>1){
                   IntentToFeedActivity();
               }else {
                   IntentToRecommendActivity();
               }
            }
        });


    }

    private void  IntentToFeedActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void  IntentToRecommendActivity(){
        Intent i = new Intent(this, RecommendActivity.class);
        startActivity(i);
        finish();
    }

    private void  IntentToFirstLoginActivity(){
        Intent i = new Intent(this, FirstLoginActivity.class);
        startActivity(i);
        finish();
    }

    private void  signInWithEmail(){
        if(!loginEmailEt.getText().toString().isEmpty() && !loginPasswordEt.getText().toString().isEmpty()  ){
            auth.signInWithEmailAndPassword(loginEmailEt.getText().toString(),loginPasswordEt.getText().toString()).addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            if(task.getResult().getUser().isEmailVerified()){
                                haveFollowing();
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
    }
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        isFirstLogin();
                    }else {
                        Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        isFirstLogin();
                    }else {
                        Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void signInWithGoolge() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInWithFacebook() {

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e(">>>", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(">>>", "Google sign in failed", e);
                // ...
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }
}