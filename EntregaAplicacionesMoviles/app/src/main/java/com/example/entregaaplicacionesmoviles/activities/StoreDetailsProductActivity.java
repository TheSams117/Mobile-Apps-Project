package com.example.entregaaplicacionesmoviles.activities;

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
import com.google.firebase.storage.FirebaseStorage;

public class StoreDetailsProductActivity extends AppCompatActivity {

    private Product product;
    private TextView productDetails,productPrice,productName;
    private ImageView productImage;
    private Button buyBtn;
    private FirebaseStorage storage;

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
        setData();
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