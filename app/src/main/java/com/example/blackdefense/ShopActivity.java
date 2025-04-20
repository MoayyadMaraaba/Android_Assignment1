package com.example.blackdefense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.Cart;
import com.example.blackdefense.dataaccess.Product;
import com.example.blackdefense.dataaccess.ProductsController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private ListView products;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ProductAdapter adapter;
    private Button cart;

    private SearchView searchInput;
    private Spinner searchSpinner;
    private Button searchBtn;
    private ProductsController productsController;
    private Button v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupSharedPrefs();
        handleEvents();

        List<String> tags = Arrays.asList("Light Gun", "Modern", "Customized", "accessories", "Ammo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);
    }

    private void setupViews() {
        products = findViewById(R.id.products);
        cart = findViewById(R.id.cart);
        searchInput = findViewById(R.id.searchInput);
        searchSpinner = findViewById(R.id.searchSpinner);
        searchBtn = findViewById(R.id.searchBtn);
        v = findViewById(R.id.v);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void handleEvents() {
        cart.setOnClickListener(e -> {
            Intent intent = new Intent(ShopActivity.this, CartActivity.class);
            startActivity(intent);
        });

        searchBtn.setOnClickListener(e -> {
            String currentQuery = searchInput.getQuery().toString();
            Log.println(Log.ASSERT, "d", currentQuery);
            String tag = searchSpinner.getSelectedItem().toString();

            ArrayList<Product> result = productsController.search(currentQuery, tag);
            adapter = new ProductAdapter(this, result);
            products.setAdapter(adapter);
        });

        v.setOnClickListener(e -> {
            Intent intent = new Intent(ShopActivity.this, OrdersActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();

        Type productsListType = new TypeToken<ArrayList<Product>>() {
        }.getType();

        productsController = new ProductsController(gson.fromJson(prefs.getString("Products",""), productsListType));

        adapter = new ProductAdapter(this, productsController.products);
        products.setAdapter(adapter);
    }



}