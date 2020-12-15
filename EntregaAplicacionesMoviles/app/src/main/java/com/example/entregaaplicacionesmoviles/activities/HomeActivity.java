package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private Button button;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        button = findViewById(R.id.button);
        button.setOnClickListener(
                view ->{
                    LoginManager.getInstance().logOut();
                    GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()).signOut();
                    auth.signOut();
                    Intent intent = new Intent(this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
                );

        if (auth.getCurrentUser()==null){
            Intent intent = new Intent(this,LoginActivity.class);
            finish();
            startActivity(intent);
            return;
        }

        Toast.makeText(this,"Bienvenido "+auth.getCurrentUser().getDisplayName(),Toast.LENGTH_LONG).show();

    }
}