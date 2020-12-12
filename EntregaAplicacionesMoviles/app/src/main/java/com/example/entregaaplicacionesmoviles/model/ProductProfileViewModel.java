package com.example.entregaaplicacionesmoviles.model;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;

public class ProductProfileViewModel extends RecyclerView.ViewHolder {
    private Button btnView;
    private ImageView imageView;
    private TextView nameView, priceView;

    public ProductProfileViewModel(@NonNull View itemView) {
        super(itemView);
        btnView = itemView.findViewById(R.id.btnView);
        imageView = itemView.findViewById(R.id.imgView);
        nameView = itemView.findViewById(R.id.nameView);
        priceView = itemView.findViewById(R.id.priceView);
    }

    public Button getBtnView() {
        return btnView;
    }

    public void setBtnView(Button btnView) {
        this.btnView = btnView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getNameView() {
        return nameView;
    }

    public void setNameView(TextView nameView) {
        this.nameView = nameView;
    }

    public TextView getPriceView() {
        return priceView;
    }

    public void setPriceView(TextView priceView) {
        this.priceView = priceView;
    }
}
