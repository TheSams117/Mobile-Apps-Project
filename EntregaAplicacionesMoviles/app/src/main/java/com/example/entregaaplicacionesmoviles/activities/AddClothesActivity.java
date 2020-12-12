package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entregaaplicacionesmoviles.R;

public class AddClothesActivity extends AppCompatActivity {
    private Button addClothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        addClothes = findViewById(R.id.addClothesBtn);
        addClothes.setOnClickListener(this::detailsClothes);
    }

    private void detailsClothes(View view) {
        Intent i = new Intent(this, ClothesDetailsActivity.class);
        startActivity(i);
    }

}