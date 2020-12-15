package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String urlfoto;
    private String details;
    private String name;
    private String price;
    private String vendedor;


    public Item(){

    }
    public Item(String urlfoto, String details, String name, String price, String vendedor) {
        this.urlfoto = urlfoto;
        this.details = details;
        this.name = name;
        this.price = price;
        this.vendedor=vendedor;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
