package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<Product> products;
    private int userId;
    private double cost;

    public Order() {
        this.id = 0;
        this.products = new ArrayList<>();
        this.userId = 0;
        this.cost = 0;
    }

    public Order(int id, ArrayList<Product> products, int userId, double cost){
        this.id = id;
        this.products = products;
        this.userId = userId;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", products=" + products +
                ", userId=" + userId +
                ", cost=" + cost +
                '}';
    }
}
