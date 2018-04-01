package com.example.android.azkya_1202153371_modul6;

/**
 * Created by Azkya Telisha Harton on 4/1/2018.
 */

public class User {
    String userId;
    String username;
    String email;

    public User() {
    }

    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
