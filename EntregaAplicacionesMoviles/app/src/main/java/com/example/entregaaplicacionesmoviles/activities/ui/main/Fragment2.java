package com.example.entregaaplicacionesmoviles.activities.ui.main;

import android.os.Bundle;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Fragment2 extends Fragment {
    View v;
    private RecyclerView ordenesNuevas;
    private RecyclerView ordenesEnviadas;
    private ArrayList<Item> ordenNueva;
    private ArrayList<Item>ordenVieja;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    public Fragment2(){
        ordenNueva=new ArrayList<>();
        ordenVieja=new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ArrayList<Item> listado=new ArrayList<>();

        ItemAdapter adapter2 = new ItemAdapter(getContext(), listado);
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("misVentas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                    enProceso.clear();
                    int index=1;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item actual=document.toObject(Item.class);
                        adapter2.addItem(actual);
                        // Item actual=new Item(document.get("urlfoto").toString(),"Orden #"+index,document.get("vendedor").toString(),"Despachada");
                        //Log.d("SSSS", "El task Se completo bien >>>>>>>>>>>>>>>>>>>>>>><< y tiene -------------------->>>>"+actual.getNombrePersona());
                        //enProceso.add(actual);
                        //index++;
                    }
                }


            }

        });
        v= inflater.inflate(R.layout.fragment2_mysales_layout,container,false);
        ordenesNuevas=v.findViewById(R.id.ordenesNuevas);
        ordenesNuevas.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordenesNuevas.setAdapter(adapter2);

        return v;
    }
}
