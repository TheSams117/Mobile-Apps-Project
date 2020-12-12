package com.example.entregaaplicacionesmoviles.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ProductProfileAdapter extends RecyclerView.Adapter<ProductProfileViewModel> {

    private ArrayList<Product> products;
    private OnProductClickListener listener;
    private FirebaseStorage storage;

    public ProductProfileAdapter() {
        products = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
    }

    public void addProduct(Product product){
        products.add(product);
        notifyDataSetChanged();
    }


    public void clear(){
        products.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductProfileViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.productview, parent, false);
        ProductProfileViewModel productViewModel = new ProductProfileViewModel(view);
        return productViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductProfileViewModel holder, int position) {
        holder.getNameView().setText(products.get(position).getName());
        holder.getPriceView().setText(products.get(position).getPrice());
        storage.getReference().child("clothes").child(products.get(position).getIdPhoto()).getDownloadUrl().addOnCompleteListener(
                task -> {
                    String url = task.getResult().toString();
                    Glide.with(holder.getImageView()).load(url).into(holder.getImageView());
                }
        );
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public interface OnProductClickListener {
        void onUserClick(Product product);
    }
}
