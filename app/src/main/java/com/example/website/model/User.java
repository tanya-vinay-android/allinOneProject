package com.example.website.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;

    public User(String id,String username,String email) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
}
