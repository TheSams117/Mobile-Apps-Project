package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entregaaplicacionesmoviles.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddClothesActivity extends AppCompatActivity {
    private Button addClothes;
    private BottomNavigationView navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        addClothes = findViewById(R.id.addClothesBtn);
        addClothes.setOnClickListener(this::detailsClothes);
        navigator = findViewById(R.id.navigatorAddClothes);
        navigator.setItemIconTintList(null);
        navigator.setSelectedItemId(R.id.addclothe);
        navigator.setOnNavigationItemSelectedListener(
                (menuItem) ->{

                    switch (menuItem.getItemId()){

                        case R.id.home:
                            Intent l = new Intent(this, FeedActivity.class);
                            startActivity(l);
                            break;

                        case R.id.addclothe:
                            Intent j = new Intent(this, AddClothesActivity.class);
                            startActivity(j);
                            break;

                        case R.id.purchasesSales:

                            break;

                        case R.id.profile:
                            Intent k = new Intent(this, PerfilActivity.class);
                            startActivity(k);
                            break;
                    }

                    return true;
                }
        );
    }

    private void detailsClothes(View view) {
        Intent i = new Intent(this, ClothesDetailsActivity.class);
        startActivity(i);
    }

}