package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/10/31.
 */

public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [ username=" + username + ", password=" + password + "]";
    }

}

