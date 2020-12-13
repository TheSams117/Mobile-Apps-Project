package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Order;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConsultSaleActivity extends AppCompatActivity implements View.OnClickListener {

    private Order order;

    private ImageView fotoIV;
    private TextView nameUserTV;
    private TextView nombreUserTV;
    private TextView direccionUserTV;
    private TextView telefonoUserTV;
    private ImageView guiaIV;
    private Button guiaBTN;
    private Button despacharBTN;
    private TextView costoTotalTV;

    private FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_sale);
        db = FirebaseFirestore.getInstance();

        order = (Order) getIntent().getExtras().getSerializable("order");

        fotoIV = findViewById(R.id.fotoIV);
        nameUserTV= findViewById(R.id.nameUserTV);
        nombreUserTV= findViewById(R.id.nombreUserTV);
        direccionUserTV= findViewById(R.id.direccionUserTV);
        telefonoUserTV= findViewById(R.id.telefonoUserTV);
        guiaIV= findViewById(R.id.fotoIV);
        guiaBTN= findViewById(R.id.guiaBTN);
        despacharBTN= findViewById(R.id.despacharBTN);
        costoTotalTV = findViewById(R.id.costoTotalTV);

        nameUserTV.setText(order.getNameUser());
        nombreUserTV.setText(order.getNombreUser());
        direccionUserTV.setText(order.getDireccionUser());
        telefonoUserTV.setText(order.getTelefonoUser());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guiaBTN:
                break;
            case R.id.despacharBTN:
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setCostoOrdenTV(costoTotalTV.getText().toString());
                myDialogFragment.setNumeroOrdenTV("posicion");
                myDialogFragment.show(getSupportFragmentManager(), "MyFragment");
                break;
        }
    }
}