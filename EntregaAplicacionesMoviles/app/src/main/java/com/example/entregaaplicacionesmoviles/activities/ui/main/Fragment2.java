package com.example.entregaaplicacionesmoviles.activities.ui.main;

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

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Fragment2 extends Fragment {
    View v;
    private RecyclerView ordenesNuevas;
    private RecyclerView ordenesEnviadas;
    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        ArrayList<Item> listado=new ArrayList<>();

        ArrayList<Item> pasados=new ArrayList<>();
        listado.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#1","Prueba User","Nueva Orden"));
        listado.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#2","Prueba User","Nueva Orden"));
        listado.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#3","Prueba User","Nueva Orden"));
        listado.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","Orden#4","Prueba User","Nueva Orden"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#1","Laura","Orden Despachada"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#2","Laura","Orden Despachada"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#3","Laura","Orden Despachada"));
        pasados.add(new Item("https://rciminternet.com/wp-content/uploads/2019/04/usuario.png","DESPACHADA#4","Laura","Orden Despachada"));

        v= inflater.inflate(R.layout.fragment2_mysales_layout,container,false);
        ordenesNuevas=v.findViewById(R.id.ordenesNuevas);
        ordenesEnviadas=v.findViewById(R.id.ordenesEnviadas);
        ItemAdapter adapter2=new ItemAdapter(getContext(),listado);
        ordenesNuevas.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordenesNuevas.setAdapter(adapter2);

        ItemAdapter ordenes=new ItemAdapter(getContext(),pasados);


        ordenesEnviadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordenesEnviadas.setAdapter(ordenes);

        Item obtener=ordenes.getItem(0);
        adapter2.addItem(obtener);
        return v;
    }
}
