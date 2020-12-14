package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.example.entregaaplicacionesmoviles.model.ProductProfileAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StoreActivity extends AppCompatActivity implements ProductProfileAdapter.OnProductClickListener {

    private TextView id;
    private RecyclerView storeProductsList;
    private ProductProfileAdapter adapter;
    private FirebaseFirestore db;
    private String idextra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent i = getIntent();
        id = findViewById(R.id.id);
        idextra = i.getStringExtra("id");

        storeProductsList = findViewById(R.id.storeProductsList);
        adapter = new ProductProfileAdapter();
        adapter.setListener(this);
        storeProductsList.setAdapter(adapter);
        storeProductsList.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        storeProductsList.setLayoutManager(manager);
        db = FirebaseFirestore.getInstance();
        setVars();
        getProductsStore();
    }

    private void getProductsStore() {
        db.collection("users").document(idextra).collection("products").addSnapshotListener(
                (value, error) -> {
                    if (value.getDocuments().size() > 0) {
                        adapter.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Log.e(">>>", document.getId() + " => " + document.getData());
                            Product product = document.toObject(Product.class);
                            adapter.addProduct(product);
                        }
                    }
                }
        );
    }

    private void setVars() {
        runOnUiThread(
                () -> {
                    id.setText(idextra);
                }
        );
    }


    @Override
    public void onUserClick(Product product) {

    }
}