package com.example.newtrackmyorder.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class User extends RealmObject{
    @PrimaryKey
    private String userId;
    private String username;
    private String currentOrderId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(String currentOrderId) {
        this.currentOrderId = currentOrderId;
    }
}
