package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;

    private int image;

    private String description;
    private double price;
    private ArrayList<String> tags;
    private int quantity;

    public Product(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.price = 0;
        this.tags = new ArrayList<>();
        this.quantity = 0;
    }

    public Product(int id, String name,int image, String description, double price, ArrayList<String> tags, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
