package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
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