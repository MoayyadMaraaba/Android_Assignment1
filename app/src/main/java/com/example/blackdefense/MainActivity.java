package com.example.blackdefense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Animation breathingLogo;
    private TextView txtLogo;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        breathingLogo = AnimationUtils.loadAnimation(this, R.anim.breathing);

        txtLogo = findViewById(R.id.txtLogo);

        txtLogo.setAnimation(breathingLogo);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        Gson gson = new Gson();

        ArrayList<Product> products1 = new ArrayList<>();

        ArrayList<String> tags1 = new ArrayList<>(Arrays.asList("Light Gun", "Modern", "Customized"));
        products1.add(new Product(1,"M4 Carbine",R.drawable.product1, "Customized M4 Carbine", 5000,tags1,5));

        ArrayList<String> tags2 = new ArrayList<>(Arrays.asList("Light Gun", "Modern"));
        products1.add(new Product(2,"Scar L",R.drawable.product2,"Light AR Rifle", 499.99,tags2, 10));

        ArrayList<String> tags3 = new ArrayList<>(Arrays.asList("Ammo"));
        products1.add(new Product(3,"Ammunition",R.drawable.product3, "Ammunition", 79.99,tags3,100));

        ArrayList<String> tags4 = new ArrayList<>(Arrays.asList("Ammo","accessories"));
        products1.add(new Product(4,"Land Mine",R.drawable.product4, "Land Mine", 449.99,tags4,50));

        ArrayList<String> tags5 = new ArrayList<>(Arrays.asList("Ammo", "accessories"));
        products1.add(new Product(5,"Hand Grenade",R.drawable.product5, "Hand Grenade", 29.99,tags5,30));

        ArrayList<String> tags6 = new ArrayList<>(Arrays.asList("accessories"));
        products1.add(new Product(6,"Laser Sight",R.drawable.product6, "Laser Sight", 15, tags6,15));


        editor.putString("Users", "");
        editor.putString("Carts", "");
        editor.putString("Products", gson.toJson(products1));
//        editor.putString("Orders", "");

        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }, 3500);
    }
}