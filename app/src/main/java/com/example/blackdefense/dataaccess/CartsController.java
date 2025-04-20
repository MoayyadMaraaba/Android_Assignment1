package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class CartsController {
    public ArrayList<Cart> carts;

    public CartsController() {
        this.carts = new ArrayList<>();
    }

    public CartsController(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    public void addCart(int userId) {
        this.carts.add(new Cart(this.carts.size() + 1, userId));
    }

    public boolean addToCart(Product product, int userId) {
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getUserID() == userId) {
                carts.get(i).getProducts().add(product);
                return true;
            }
        }
        return false;
    }
}
