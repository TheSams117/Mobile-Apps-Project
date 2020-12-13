package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.example.entregaaplicacionesmoviles.model.ProductProfileAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilActivity extends AppCompatActivity implements ProductProfileAdapter.OnProductClickListener {

    private RecyclerView productsList;
    private ProductProfileAdapter adapter;
    private FirebaseFirestore db;
    private TextView productsSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        productsList = findViewById(R.id.productsList);
        adapter = new ProductProfileAdapter();
        adapter.setListener(this);
        productsList.setAdapter(adapter);
        productsList.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        productsList.setLayoutManager(manager);
        db = FirebaseFirestore.getInstance();
        productsSize = findViewById(R.id.productsSizeTv);
        getProducts();
    }

    private void getProducts() {
        db.collection("users").document("55OsQAwJpIYmaPZxmDB6GHn8AhB3").collection("products").addSnapshotListener(
                (value, error) -> {
                    Log.e(">>>",Integer.toString(value.getDocuments().size()));
                    if(value.getDocuments().size() > 0) {
                        adapter.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Log.e(">>>", document.getId() + " => " + document.getData());
                            Product product = document.toObject(Product.class);
                            adapter.addProduct(product);
                        }
                    }else{
                        runOnUiThread(
                                ()->{
                                    Toast.makeText(this,"No tienes ningún producto",Toast.LENGTH_SHORT).show();
                                }
                        );
                    }
                }
        );
    }


    @Override
    public void onUserClick(Product product) {

    }
}