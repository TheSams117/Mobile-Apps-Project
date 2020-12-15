package com.example.entregaaplicacionesmoviles.activities.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Item;
import com.example.entregaaplicacionesmoviles.model.ItemAdapter;
import com.example.entregaaplicacionesmoviles.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Fragment1 extends Fragment implements ItemAdapter.OnItemClickListener {
    View v;
    private RecyclerView comprasProceso;
    private RecyclerView comprasPasadas;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public Fragment1() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         ArrayList<Item> enProceso=new ArrayList<>();
         ArrayList<Item> cPasadas=new ArrayList<>();
        ItemAdapter adapter = new ItemAdapter(getContext(), enProceso);
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("misCompras").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                    enProceso.clear();
                    int index=1;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item actual=document.toObject(Item.class);
                        adapter.addItem(actual);
                        // Item actual=new Item(document.get("urlfoto").toString(),"Orden #"+index,document.get("vendedor").toString(),"Despachada");
                        //Log.d("SSSS", "El task Se completo bien >>>>>>>>>>>>>>>>>>>>>>><< y tiene -------------------->>>>"+actual.getNombrePersona());
                        //enProceso.add(actual);
                        //index++;
                    }
                }


            }

        });
        //enProceso.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#1","Prueba User","Nueva Orden"));
        //Poner las listas dentro del metodo oncreateview
        v = inflater.inflate(R.layout.fragment1_mypurchases_layout, container, false);
        comprasProceso = v.findViewById(R.id.comprasEnProceso);
        comprasPasadas = v.findViewById(R.id.comprasPasadas);

        comprasProceso.setLayoutManager(new LinearLayoutManager(getActivity()));
        comprasProceso.setAdapter(adapter);

        ItemAdapter pasadas = new ItemAdapter(getContext(), cPasadas);


        comprasPasadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        comprasPasadas.setAdapter(pasadas);
        return v;
    }
//public RecyclerView.ViewHolder onCreateViewHolder(){

    //}
    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void OnClickListener(Item item) {

    }

    public void loadItems() {

    }


}
