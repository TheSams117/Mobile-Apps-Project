package com.example.entregaaplicacionesmoviles.model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;

public class ItemViewModel extends RecyclerView.ViewHolder {
    private Button botonItem;
    private TextView title;
    private TextView subtitle;
    private ImageView imagen;

    public ItemViewModel(@NonNull View itemView) {
        super(itemView);
        botonItem=itemView.findViewById(R.id.itemButton);
        title=itemView.findViewById(R.id.topTitle);
        subtitle=itemView.findViewById(R.id.subTitle);
        imagen=itemView.findViewById(R.id.userImage);
    }

    public Button getBotonItem() {
        return botonItem;
    }

    public void setBotonItem(Button botonItem) {
        this.botonItem = botonItem;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(TextView subtitle) {
        this.subtitle = subtitle;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }
}
