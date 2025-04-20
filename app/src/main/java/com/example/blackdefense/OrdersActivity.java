package com.example.blackdefense;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private ListView listView;
    private OrderAdapter adapter;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.orders);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        Gson gson = new Gson();

        Type ordersListType = new TypeToken<ArrayList<Order>>() {
        }.getType();

        ArrayList<Order> orders = gson.fromJson(prefs.getString("Orders", ""), ordersListType);

        adapter = new OrderAdapter(this, orders);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Gson gson = new Gson();
//
//        Type ordersListType = new TypeToken<ArrayList<Order>>() {
//        }.getType();
//
//        ArrayList<Order> orders = gson.fromJson(prefs.getString("Orders", ""), ordersListType);
//
//        adapter = new OrderAdapter(this, orders);
//        ordersList.setAdapter(adapter);

    }
}