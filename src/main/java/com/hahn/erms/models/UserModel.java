package com.hahn.erms.models;

import java.time.LocalDate;

public class UserModel {
    private final String username;
    private final String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
         if(this.username.equals(username) && this.password.equals(password)){
             AuthManager.username = username;
             AuthManager.token = AuthManager.username + "EFEZFEZFZEFZFZE" ;
             AuthManager.expiryDate = LocalDate.ofYearDay(2025,123);
             return true;
         }
         return false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
