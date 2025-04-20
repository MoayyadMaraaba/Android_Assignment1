package com.example.blackdefense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.Cart;
import com.example.blackdefense.dataaccess.CartsController;
import com.example.blackdefense.dataaccess.User;
import com.example.blackdefense.dataaccess.UsersController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputFullName;
    private EditText inputPassword;
    private Button btnRegister;
    private Button btnLogin;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupViews();
        handleEvents();
        setupSharedPrefs();

    }

    private void setupViews() {
        inputFullName = findViewById(R.id.inputFullName);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void handleEvents() {
        btnLogin.setOnClickListener(e -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(e -> {
            // load Data from Shared Preference
            String u = prefs.getString("Users", "");
            String c = prefs.getString("Cats", "");

            ArrayList<User> users = new ArrayList<>();
            ArrayList<Cart> carts = new ArrayList<>();

            Gson gson = new Gson();

            if (!u.isEmpty()) {
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();

                users = gson.fromJson(u, userListType);
            }

            if(!c.isEmpty()) {
                Type cartsListType = new TypeToken<ArrayList<Cart>>() {
                }.getType();

                carts = gson.fromJson(u, cartsListType);
            }


            String fullName = inputFullName.getText().toString();
            String password = inputPassword.getText().toString();

            if (!fullName.isEmpty() && !password.isEmpty()) {
                UsersController usersController = new UsersController(users);
                CartsController cartsController = new CartsController(carts);

                User isCreated = usersController.createUser(fullName, password);

                if (isCreated == null) {
                    Toast.makeText(RegisterActivity.this, "FullName already exists", Toast.LENGTH_SHORT).show();
                }

                if (isCreated != null) {
                    // Save the user
                    String newUsers = gson.toJson(usersController.users);
                    editor.putString("Users", newUsers);
                    editor.putString("LoggedUser", gson.toJson(isCreated));


                    // Create Cart for the User
                    cartsController.addCart(isCreated.getId());
                    editor.putString("Carts", gson.toJson(cartsController.carts));

                    editor.commit();

                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(RegisterActivity.this, ShopActivity.class);
                        startActivity(intent);
                        finish();
                    }, 1000);
                }
            }

        });
    }


}