package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.example.entregaaplicacionesmoviles.model.ProductProfileAdapter;
import com.example.entregaaplicacionesmoviles.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class StoreActivity extends AppCompatActivity implements ProductProfileAdapter.OnProductClickListener {

    private TextView nameStore,descriptionStore;
    private ImageView imageStore;
    private RecyclerView storeProductsList;
    private ProductProfileAdapter adapter;
    private FirebaseFirestore db;
    private String idextra;
    private BottomNavigationView navigator;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent i = getIntent();
        nameStore = findViewById(R.id.nameStore);
        imageStore = findViewById(R.id.imageStore);
        idextra = i.getStringExtra("id");

        storeProductsList = findViewById(R.id.storeProductsList);
        descriptionStore = findViewById(R.id.descriptionStore);
        adapter = new ProductProfileAdapter();
        adapter.setListener(this);
        storeProductsList.setAdapter(adapter);
        storeProductsList.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        storeProductsList.setLayoutManager(manager);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        getProductsStore();
        navigator = findViewById(R.id.navigatorStore);
        navigator.setItemIconTintList(null);
        navigator.setSelectedItemId(R.id.home);
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

        if(storage.getReference().child("profiles").child("photo").child(idextra).getDownloadUrl() != null){
            storage.getReference().child("profiles").child("photo").child(idextra).getDownloadUrl().addOnCompleteListener(
                    task -> {
                        Glide.with(imageStore).load(task.getResult().toString()).into(imageStore);
                    }
            );
        }

        db.collection("users").document(idextra).get().addOnCompleteListener(
                task -> {
                    DocumentSnapshot document = task.getResult();
                    User user = document.toObject(User.class);
                    nameStore.setText(user.getName());
                    descriptionStore.setText(user.getDescription());
                }
        );
    }



    @Override
    public void onUserClick(Product product) {
        Intent j= new Intent(this, StoreDetailsProductActivity.class);
        j.putExtra("product", product);
        j.putExtra("NombreComprador",idextra);
        j.putExtra("nombreVendedor",nameStore.getText());
        startActivity(j);
    }
}