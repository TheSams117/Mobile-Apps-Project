package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;

public class Order implements Serializable {

    //private ImageView fotoIV;
    private String nameUser;
    private String nombreUser;
    private String direccionUser;
    private String telefonoUser;
    private String costoTotal;
    private String estado;
    //private ImageView guiaIV;

    public Order() {
    }

    public Order(String nameUser, String nombreUser, String direccionUser, String telefonoUser, String costoTotal, String estado) {
        this.nameUser = nameUser;
        this.nombreUser = nombreUser;
        this.direccionUser = direccionUser;
        this.telefonoUser = telefonoUser;
        this.costoTotal = costoTotal;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getDireccionUser() {
        return direccionUser;
    }

    public void setDireccionUser(String direccionUser) {
        this.direccionUser = direccionUser;
    }

    public String getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(String telefonoUser) {
        this.telefonoUser = telefonoUser;
    }
}
