package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String details;
    private String price;
    private String photo;

    public Product(){

    }

    public Product(String name, String details, String price, String photo) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.photo = photo;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
