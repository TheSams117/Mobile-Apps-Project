package com.example.entregaaplicacionesmoviles.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreModel implements Serializable {

    private Product[] products;
    private String name;
    private String id;

    public StoreModel() {
        this.products = new Product[3];
    }

    public StoreModel(String name, String id) {
        this.products = new Product[3];
        this.name = name;
        this.id = id;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

