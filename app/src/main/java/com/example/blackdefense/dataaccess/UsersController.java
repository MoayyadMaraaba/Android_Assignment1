package com.example.blackdefense.dataaccess;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.blackdefense.RegisterActivity;

import java.util.ArrayList;

public class UsersController {

    public ArrayList<User> users;

    public UsersController() {
        this.users = new ArrayList<>();
    }

    public UsersController(ArrayList<User> users) {
        this.users = users;
    }

    public User createUser(String fullName, String password) {
        if (!fullName.isEmpty() && !password.isEmpty()) {
            if (!isFullNameAlreadyExists(fullName)) {
                User u = new User(this.users.size() + 1, fullName, password);
                users.add(u);
                return u;
            }
        }
        return null;
    }

    public boolean isFullNameAlreadyExists(String fullName) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getFullName().equals(fullName)) {
                return true;
            }
        }
        return false;
    }

    public User login(String fullName, String password) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getFullName().equals(fullName) && users.get(i).getPassword().equals(password)) {
                return users.get(i);
            }
        }
        return null;
    }


}
