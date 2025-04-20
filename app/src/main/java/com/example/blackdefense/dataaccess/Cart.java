package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class Cart {
    private int id;
    private int userID;

    private ArrayList<Product> products;


    public Cart() {

    }

    public Cart(int id, int userID) {
        this.id = id;
        this.userID = userID;
        this.products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userID=" + userID +
                ", products=" + products +
                '}';
    }
}
