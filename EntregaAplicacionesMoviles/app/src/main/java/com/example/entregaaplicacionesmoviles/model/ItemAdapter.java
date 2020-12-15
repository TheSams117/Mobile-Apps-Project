package com.example.entregaaplicacionesmoviles.model;

import android.content.Context;
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

public class ItemAdapter extends RecyclerView.Adapter<ItemViewModel> {


    private ArrayList<Item> lista;
    private Context context;
    private FirebaseStorage storage;

    public ItemAdapter(Context context, ArrayList<Item> data) {
        this.context = context;
        this.lista = data;
        storage = FirebaseStorage.getInstance();
    }

    public void addItem(Item item) {
        lista.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        lista.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards, parent, false);
        ItemViewModel m = new ItemViewModel(view);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewModel holder, int position) {
        Item n = lista.get(position);
        holder.getTitle().setText(n.getDetails());
        holder.getSubtitle().setText(n.getName());
        holder.getState().setText(n.getPrice());
        Log.d(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", n.getUrlfoto()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        storage.getReference().child("profiles").child("photo").child(n.getUrlfoto()).getDownloadUrl().addOnCompleteListener(
                task -> {
                    Glide.with(holder.getImagen()).load(task.getResult().toString()).into(holder.getImagen());
                }
        );


        //Glide.with(holder.getImagen()).load(n.getUrlFoto()).into(holder.getImagen());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public Item getItem(int indice) {
        Item n = this.lista.remove(indice);
        notifyDataSetChanged();
        return n;
    }

    public interface OnItemClickListener {
        void OnClickListener(Item item);
    }
}
