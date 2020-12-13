package com.example.entregaaplicacionesmoviles.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.entregaaplicacionesmoviles.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyDialogFragment extends DialogFragment implements  View.OnClickListener{

    private TextView numOrdenTV;
    private TextView costoOrdenTV;
    private Button despacharBtn;

    private String dataNumOrden;
    private String dataCostoOrden;

    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        numOrdenTV = view.findViewById(R.id.numOrdenTV);
        costoOrdenTV = view.findViewById(R.id.costoOrdenTV);
        despacharBtn = view.findViewById(R.id.despacharBtn);

        numOrdenTV.setText(dataNumOrden);
        costoOrdenTV.setText(dataCostoOrden);

        despacharBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.despacharBtn:
                //Cambiar el estado de la orden
                break;
        }
    }

    public void setNumeroOrdenTV(String data){
        this.dataNumOrden = data;
    }

    public void setCostoOrdenTV(String data){
        this.dataCostoOrden = data;
    }

}
