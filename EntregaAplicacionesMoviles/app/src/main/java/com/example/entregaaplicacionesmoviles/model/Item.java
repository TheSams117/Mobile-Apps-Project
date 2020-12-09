package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String urlFoto;
    private String encabezado1;
    private String nombrePersona;
    private String estado;


    
    public Item(String urlFoto, String encabezado1, String nombrePersona, String estado) {
        this.urlFoto = urlFoto;
        this.encabezado1 = encabezado1;
        this.nombrePersona = nombrePersona;
        this.estado = estado;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getEncabezado1() {
        return encabezado1;
    }

    public void setEncabezado1(String encabezado1) {
        this.encabezado1 = encabezado1;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
