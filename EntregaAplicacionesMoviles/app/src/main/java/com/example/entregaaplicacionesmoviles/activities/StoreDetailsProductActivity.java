package com.example.entregaaplicacionesmoviles.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class StoreDetailsProductActivity extends AppCompatActivity {

    private Product product;
    private TextView productDetails,productPrice,productName;
    private ImageView productImage;
    //Este es el boton que hara la accion de compra y pasara al otro intent de recycle view
    private Button buyBtn;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String buscarNombre;
    private String vendedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details_product);

        productDetails = findViewById(R.id.productDetailsSDP);
        productPrice =findViewById(R.id.productPriceSDP);
        productName = findViewById(R.id.productNameSDP);
        productImage = findViewById(R.id.productImageSDP);
        buyBtn = findViewById(R.id.buyBtnSDP);
        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("product");
        storage = FirebaseStorage.getInstance();
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        setData();
        buscarNombre= i.getStringExtra("NombreComprador");
        vendedor=i.getStringExtra("nombreVendedor");
        Log.d(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", buscarNombre);
        //Boton
        buyBtn.setOnClickListener((v)->{
    Map<String, String> data=new HashMap<>();
            data.put("name",product.getName());
            data.put("urlfoto",buscarNombre);
            data.put("price",product.getPrice());
            data.put("details",product.getDetails());
            data.put("vendedor",vendedor);
            //Pone esto en productos comprados por el actual comprador
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("misCompras").add(data);

            //pone esto en productos vendidos al comprador
            db.collection("users").document(buscarNombre).collection("misVentas").add(data);
            Intent intent = new Intent(this,PurchasesAndSalesActivity.class);
            finish();
            startActivity(intent);
        });

    }

    private void setData() {
        runOnUiThread(
                ()->{
                    productDetails.setText(product.getDetails());
                    productPrice.setText(product.getPrice());
                    productName.setText(product.getName());
                    storage.getReference().child("clothes").child(product.getPhoto()).getDownloadUrl().addOnCompleteListener(
                            task -> {
                                Glide.with(productImage).load(task.getResult().toString()).into(productImage);
                            }
                    );
                }
        );
    }
}