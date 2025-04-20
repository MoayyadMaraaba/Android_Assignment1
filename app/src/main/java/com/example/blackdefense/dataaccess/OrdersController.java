package com.example.blackdefense.dataaccess;

import java.util.ArrayList;

public class OrdersController {
    public ArrayList<Order> orders;

    public OrdersController() {
        this.orders = new ArrayList<>();
    }

    public OrdersController(ArrayList<Order> orders) {
        this.orders = orders;
    }


    public void placeOrder(ArrayList<Product> products, int userId, double cost) {
        Order order = new Order(this.orders.size() + 1, products, userId, cost);
        orders.add(order);
    }


}
