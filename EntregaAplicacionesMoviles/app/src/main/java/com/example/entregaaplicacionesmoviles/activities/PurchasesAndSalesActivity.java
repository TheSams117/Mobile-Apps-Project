package com.example.entregaaplicacionesmoviles.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.ItemAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.entregaaplicacionesmoviles.activities.ui.main.SectionsPagerAdapter;

public class PurchasesAndSalesActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases_and_sales);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#000000"));
        viewPager.setBackground(Drawable.createFromPath("0xFFF5E4"));

        navigator = findViewById(R.id.navigatorPurchases);
        navigator.setItemIconTintList(null);
        navigator.setSelectedItemId(R.id.purchasesSales);

        navigator.setOnNavigationItemSelectedListener(
                (menuItem) ->{

                    switch (menuItem.getItemId()){

                        case R.id.home:
                            Intent i = new Intent(this, FeedActivity.class);
                            startActivity(i);
                            break;

                        case R.id.addclothe:
                            Intent j = new Intent(this, AddClothesActivity.class);
                            startActivity(j);
                            break;

                        case R.id.purchasesSales:
                            Intent n = new Intent(this, PurchasesAndSalesActivity.class);
                            startActivity(n);
                            break;

                        case R.id.profile:
                            Intent k = new Intent(this, PerfilActivity.class);
                            startActivity(k);
                            break;
                    }

                    return true;
                }
        );

       // adapter=new ItemAdapter();
       // comprasEnProceso.setAdapter(adapter);
       // comprasEnProceso.setHasFixedSize(true);
       // LinearLayoutManager manager= new LinearLayoutManager(this);
       // comprasEnProceso.setLayoutManager(manager);

        //comprasPasadas.setAdapter(adapter);
        //comprasPasadas.setHasFixedSize(true);
        //comprasPasadas.setLayoutManager(manager);
       // comprasEnProceso=findViewById(R.id.comprasEnProceso);
        //comprasEnProceso=findViewById(R.id.comprasEnProceso);
    }

    @Override
    public void onClick(View v) {

    }
}