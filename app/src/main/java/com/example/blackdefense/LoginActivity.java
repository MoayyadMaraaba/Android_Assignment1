package com.example.blackdefense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blackdefense.dataaccess.User;
import com.example.blackdefense.dataaccess.UsersController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText inputFullName;
    private EditText inputPassword;
    private Button btnLogin;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupSharedPrefs();
        handleEvents();

    }

    private void setupViews() {
        inputFullName = findViewById(R.id.inputFullName);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void handleEvents() {

        btnLogin.setOnClickListener(e -> {

            String u = prefs.getString("Users", "");

            ArrayList<User> users = new ArrayList<>();
            Gson gson = new Gson();

            if (!u.isEmpty()) {
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();

                users = gson.fromJson(u, userListType);
            }


            String fullName = inputFullName.getText().toString();
            String password = inputPassword.getText().toString();


            if (!fullName.isEmpty() && !password.isEmpty()) {
                UsersController usersController = new UsersController(users);
                User loggedUser = usersController.login(fullName, password);
                if (loggedUser != null) {
                    editor.putString("LoggedUser", gson.toJson(loggedUser));
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, ShopActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}