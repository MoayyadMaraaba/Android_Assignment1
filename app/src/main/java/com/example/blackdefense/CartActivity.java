package com.example.blackdefense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.Cart;
import com.example.blackdefense.dataaccess.Order;
import com.example.blackdefense.dataaccess.OrdersController;
import com.example.blackdefense.dataaccess.Product;
import com.example.blackdefense.dataaccess.ProductsController;
import com.example.blackdefense.dataaccess.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ListView products;
    private CartAdapter adapter;

    private TextView total;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Cart userCart = new Cart();

    private Button check;
    private double sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupSharedPrefs();
        loadProductsFromCart();
        calculateTotal();
        checkOut();
    }

    private void setupViews() {
        check = findViewById(R.id.check);
        products = findViewById(R.id.products);
        total = findViewById(R.id.total);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void loadProductsFromCart() {
        Gson gson = new Gson();
        Type cartsListType = new TypeToken<ArrayList<Cart>>() {
        }.getType();

        ArrayList<Cart> carts = gson.fromJson(prefs.getString("Carts", ""), cartsListType);
        User loggedUser = gson.fromJson(prefs.getString("LoggedUser", ""), User.class);

        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getUserID() == loggedUser.getId()) {
                userCart = carts.get(i);
            }
        }

        adapter = new CartAdapter(this, userCart.getProducts());
        products.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProductsFromCart();
    }

    private void calculateTotal() {
        sum = 0;
        for (int i = 0; i < userCart.getProducts().size(); i++) {
            sum += userCart.getProducts().get(i).getPrice();
        }

        total.setText("Total: " + sum);
    }

    private void checkOut() {
        check.setOnClickListener(e -> {
            if (!userCart.getProducts().isEmpty()) {
                Gson gson = new Gson();

                Type productsListType = new TypeToken<ArrayList<Product>>() {
                }.getType();

                ArrayList<Product> products = gson.fromJson(prefs.getString("Products", ""), productsListType);
                ArrayList<Product> cartProducts = userCart.getProducts();

                // Updating Products Quantity
                for (int i = 0; i < cartProducts.size(); i++) {
                    for (int j = 0; j < products.size(); j++) {
                        if (cartProducts.get(i).getId() == products.get(j).getId()) {
                            int currentQuantity = products.get(j).getQuantity();
                            products.get(j).setQuantity(currentQuantity - 1);
                        }
                    }
                }

                editor.putString("Products", gson.toJson(products));
                editor.commit();

                // Create Order
                User loggedUser = gson.fromJson(prefs.getString("LoggedUser", ""), User.class);

                Type ordersListType = new TypeToken<ArrayList<Order>>() {
                }.getType();

                OrdersController ordersController;

                if (!prefs.getString("Orders", "").equals("")) {
                    ordersController = new OrdersController(gson.fromJson(prefs.getString("Orders", ""), ordersListType));
                } else {
                    ordersController = new OrdersController();
                }

                ordersController.placeOrder(userCart.getProducts(), loggedUser.getId(), sum);

                editor.putString("Orders", gson.toJson(ordersController.orders));
                editor.commit();


                // Clear Carts
                Type cartsListType = new TypeToken<ArrayList<Cart>>() {
                }.getType();

                ArrayList<Cart> carts = gson.fromJson(prefs.getString("Carts", ""), cartsListType);

                for (int i = 0; i < carts.size(); i++) {
                    if (carts.get(i).getId() == userCart.getId()) {
                        carts.get(i).getProducts().clear();
                    }
                }

                editor.putString("Carts", gson.toJson(carts));
                editor.commit();

                Intent intent = new Intent(CartActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }
}