package com.example.entregaaplicacionesmoviles.model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entregaaplicacionesmoviles.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewModel> {

    private ArrayList<StoreModel> storeModels;
    private OnStoreClickListener listener;
    private FirebaseStorage storage;

    public StoreAdapter() {
        storeModels = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
    }

    public void addStore(StoreModel storeModel){
        storeModels.add(storeModel);
        notifyDataSetChanged();
    }


    public void clear(){
        storeModels.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.storeview, parent, false);
        StoreViewModel storeViewModel = new StoreViewModel(view);
        return storeViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewModel holder, int position) {
        holder.getStoreName().setText(storeModels.get(position).getName());
        if(storeModels.get(position).getProducts()[0] !=null) holder.getProductOneName().setText(storeModels.get(position).getProducts()[0].getName());
        if(storeModels.get(position).getProducts()[1] !=null) holder.getProductTwoName().setText(storeModels.get(position).getProducts()[1].getName());
        if(storeModels.get(position).getProducts()[2] !=null) holder.getProductThreeName().setText(storeModels.get(position).getProducts()[2].getName());
        if(storeModels.get(position).getProducts()[0] !=null) holder.getProductPriceOne().setText(storeModels.get(position).getProducts()[0].getPrice());
        if(storeModels.get(position).getProducts()[1] !=null) holder.getProductPriceTwo().setText(storeModels.get(position).getProducts()[1].getPrice());
        if(storeModels.get(position).getProducts()[2] !=null) holder.getProductPriceThree().setText(storeModels.get(position).getProducts()[2].getPrice());

        if(storeModels.get(position).getProducts()[0] !=null) {
            storage.getReference().child("clothes").child(storeModels.get(position).getProducts()[0].getPhoto()).getDownloadUrl().addOnCompleteListener(
                    task -> {
                        Glide.with(holder.getProductOneImage()).load(task.getResult().toString()).into(holder.getProductOneImage());
                    }
            );
        }
        if(storeModels.get(position).getProducts()[1] !=null) {
            storage.getReference().child("clothes").child(storeModels.get(position).getProducts()[1].getPhoto()).getDownloadUrl().addOnCompleteListener(
                    task -> {
                        Glide.with(holder.getProductTwoImage()).load(task.getResult().toString()).into(holder.getProductTwoImage());
                    }
            );
        }
        if(storeModels.get(position).getProducts()[2] !=null) {
            storage.getReference().child("clothes").child(storeModels.get(position).getProducts()[2].getPhoto()).getDownloadUrl().addOnCompleteListener(
                    task -> {
                        Glide.with(holder.getProductThreeImage()).load(task.getResult().toString()).into(holder.getProductThreeImage());
                    }
            );
        }
        holder.getSeeAll().setOnClickListener(
                v->{
                    StoreModel storeModel =storeModels.get(position);
                    listener.onUserClick(storeModel);
                }
        );
    }

    @Override
    public int getItemCount() {
        return storeModels.size();
    }

    public void setListener(OnStoreClickListener listener) {
        this.listener = listener;
    }

    public interface OnStoreClickListener {
        void onUserClick(StoreModel storeModel);
    }
}
