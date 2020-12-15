package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverPasswordActivity extends AppCompatActivity {
    
    private Button recoverBtn;
    private EditText emailET;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        
        recoverBtn = findViewById(R.id.recoverBtn);
        emailET = findViewById(R.id.emailET);
        auth = FirebaseAuth.getInstance();
        
        recoverBtn.setOnClickListener(this::onClickListener);
    }

    private void onClickListener(View view) {
        if(!emailET.getText().toString().isEmpty()){
            auth.sendPasswordResetEmail(emailET.getText().toString()).addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }else {
            Toast.makeText(this,"Ingresa un correo electronico registrado",Toast.LENGTH_LONG).show();
        }

    }
}