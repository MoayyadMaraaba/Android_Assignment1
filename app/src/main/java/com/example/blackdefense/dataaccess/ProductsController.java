package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class ProductsController {
    public ArrayList<Product> products;

    public ProductsController() {
        this.products = new ArrayList<>();
    }

    public ProductsController(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> search(String text, String tag) {
        ArrayList<Product> products1 = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getName().contains(text) && products.get(i).getTags().contains(tag)) {
                products1.add(products.get(i));
            }
        }
        return products1;
    }

}
