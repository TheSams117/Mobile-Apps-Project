package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.google.firebase.storage.FirebaseStorage;

public class EditProductActivity extends AppCompatActivity {

    private Product product;
    private TextView productDetails,productPrice,productName;
    private ImageView productImage;
    private Button editProductBtn;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent i = getIntent();

        product = (Product) i.getSerializableExtra("product");
        productDetails = findViewById(R.id.productDetailsTV);
        productPrice = findViewById(R.id.productPriceTV);
        productName = findViewById(R.id.productNameTV);
        productImage = findViewById(R.id.productImageView);
        editProductBtn = findViewById(R.id.editClothesBtn);
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
                                Log.e(">>>>>>",task.getResult().toString());
                                Glide.with(productImage).load(task.getResult().toString()).into(productImage);
                            }
                    );
                }
        );
    }


}