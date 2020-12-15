package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.example.entregaaplicacionesmoviles.model.StoreAdapter;
import com.example.entregaaplicacionesmoviles.model.StoreModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity implements StoreAdapter.OnStoreClickListener {

    private RecyclerView storeList;
    private StoreAdapter adapter;
    private FirebaseFirestore db;
    private ArrayList<String> ids;
    private BottomNavigationView navigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        storeList = findViewById(R.id.storeList);
        adapter = new StoreAdapter();
        adapter.setListener(this);
        storeList.setAdapter(adapter);
        storeList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        storeList.setLayoutManager(manager);
        db = FirebaseFirestore.getInstance();
        ids = new ArrayList<>();
        navigator = findViewById(R.id.navigator);
        navigator.setItemIconTintList(null);

        getIds();
        getStores();

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

    private void getIds() {
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("following").addSnapshotListener(
                (value, error) -> {
                    if (value.getDocuments().size() > 0) {
                        ids.clear();
                        adapter.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Log.e(">>>", document.getId() + " => " + document.getData());
                            ids.add(document.getId());
                            getStores();
                        }
                    }
                }
        );
    }

    private void getStores() {
        for (int i = 0; i < ids.size(); i++) {
            int finalI = i;
            db.collection("users").document(ids.get(i)).get().addOnCompleteListener(
                    task -> {
                            DocumentSnapshot document = task.getResult();
                            Log.e(">>>", document.getId() + " => " + document.getData());
                            StoreModel storeModel = document.toObject(StoreModel.class);
                            db.collection("users").document(ids.get(finalI)).collection("products").limit(3).get().addOnCompleteListener(
                                    task1 -> {
                                        Product[] products = new Product[3];
                                        int index = 0;
                                        for (DocumentSnapshot child: task1.getResult()){
                                            Product product = child.toObject(Product.class);
                                            products[index] = product;
                                            index++;
                                            Log.e(">>>>", child.getData().toString());
                                        }
                                        storeModel.setProducts(products);
                                        adapter.addStore(storeModel);
                                    }
                            );
                    }
            );
        }
    }

    @Override
    public void onUserClick(StoreModel storeModel) {
        Intent j= new Intent(this, StoreActivity.class);
        j.putExtra("id", storeModel.getId());
        startActivity(j);
    }
}