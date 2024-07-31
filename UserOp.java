package com.mansi.spotify.operations;

import com.mansi.spotify.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserOp {
    private Map<String, User> usersDatabase = new HashMap<>();

    public boolean signup(User user) {
        if (usersDatabase.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        usersDatabase.put(user.getUsername(), user);
        return true;
    }

    public boolean login(String username, String password) {
        User user = usersDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
