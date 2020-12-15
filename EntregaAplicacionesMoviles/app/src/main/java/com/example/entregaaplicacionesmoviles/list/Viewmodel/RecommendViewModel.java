package com.example.entregaaplicacionesmoviles.list.Viewmodel;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;

public class RecommendViewModel extends RecyclerView.ViewHolder {
    private Button actionRow;
    private ImageView imageRow;
    private TextView nameRow;
    public RecommendViewModel(@NonNull View itemView) {
        super(itemView);
        imageRow = itemView.findViewById(R.id.imgRow);
        nameRow = itemView.findViewById(R.id.nameRow);
        actionRow = itemView.findViewById(R.id.actionRow);
    }


    public Button getActionRow() {
        return actionRow;
    }

    public ImageView getImageRow() {
        return imageRow;
    }

    public TextView getNameRow() {
        return nameRow;
    }
}
