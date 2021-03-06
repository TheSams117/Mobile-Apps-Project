package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.example.entregaaplicacionesmoviles.model.ProductProfileAdapter;
import com.example.entregaaplicacionesmoviles.model.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class PerfilActivity extends AppCompatActivity implements ProductProfileAdapter.OnProductClickListener {

    private RecyclerView productsList;
    private ProductProfileAdapter adapter;
    private FirebaseFirestore db;
    private TextView descriptionProfile,nameUserProfile;
    private FirebaseStorage storage;
    private ImageView imageProfile;
    private BottomNavigationView navigator;
    private Button logoutBtn;

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
        imageProfile = findViewById(R.id.imageProfilePerfil);
        descriptionProfile = findViewById(R.id.descriptionProfile);
        nameUserProfile = findViewById(R.id.nameUserProfile);
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this::logout);
        storage = FirebaseStorage.getInstance();
        navigator = findViewById(R.id.navigatorProfile);
        navigator.setItemIconTintList(null);
        navigator.setSelectedItemId(R.id.profile);
        getProducts();

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
    }

    private void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()).signOut();
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
        finish();
    }

    private void getProducts() {
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("products").addSnapshotListener(
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

        storage.getReference().child("profiles").child("photo").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getDownloadUrl().addOnCompleteListener(
                task -> {
                    Glide.with(imageProfile).load(task.getResult().toString()).into(imageProfile);
                }
        );
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(
                task -> {
                    DocumentSnapshot document = task.getResult();
                    User user = document.toObject(User.class);
                    nameUserProfile.setText(user.getName());
                    descriptionProfile.setText(user.getDescription());
                }
        );
    }


    @Override
    public void onUserClick(Product product) {
        Intent j= new Intent(this, EditProductActivity.class);
        j.putExtra("product", product);
        startActivity(j);
    }
}