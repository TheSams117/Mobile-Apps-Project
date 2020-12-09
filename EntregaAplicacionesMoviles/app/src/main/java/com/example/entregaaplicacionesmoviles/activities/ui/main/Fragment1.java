package com.example.entregaaplicacionesmoviles.activities.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.model.Item;
import com.example.entregaaplicacionesmoviles.model.ItemAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Fragment1 extends Fragment {
    View v;
    private RecyclerView comprasProceso;
    private RecyclerView comprasPasadas;

    public Fragment1(){

    }
    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
         ArrayList<Item> lista=new ArrayList<>();
         ArrayList<Item> pasados=new ArrayList<>();
        lista.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#1","INCOGNITO","Denegado"));
        lista.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#2","INCOGNITO","Denegado"));
        lista.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#3","INCOGNITO","Denegado"));
        lista.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#4","INCOGNITO","Denegado"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#1","INCOGNITO","Denegado"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#2","INCOGNITO","Denegado"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#3","INCOGNITO","Denegado"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#4","INCOGNITO","Denegado"));

        //Poner las listas dentro del metodo oncreateview
        v= inflater.inflate(R.layout.fragment1_mypurchases_layout,container,false);
        comprasProceso=v.findViewById(R.id.comprasEnProceso);
        comprasPasadas=v.findViewById(R.id.comprasPasadas);
        ItemAdapter adapter=new ItemAdapter(getContext(),lista);
        comprasProceso.setLayoutManager(new LinearLayoutManager(getActivity()));
        comprasProceso.setAdapter(adapter);

        ItemAdapter pasadas=new ItemAdapter(getContext(),pasados);


        comprasPasadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        comprasPasadas.setAdapter(pasadas);

        Item obtener=pasadas.getItem(0);
        adapter.addItem(obtener);
        return v;
    }
//public RecyclerView.ViewHolder onCreateViewHolder(){

//}
    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
