package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String details;
    private int price;
    private String idPhoto;

    public Product(){

    }

    public Product(String name, String details, int price, String idPhoto) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.idPhoto = idPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }
}
