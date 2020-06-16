package com.example.website.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("nonce")
    private String nonce;

    @SerializedName("cookie")
    private String cookie;

    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private String status;

    @SerializedName("valid")
    private boolean valid;




    public LoginResponse(String nonce, String cookie, boolean valid,String status,User user) {
        this.nonce = nonce;
        this.cookie = cookie;
        this.valid = valid;
        this.status = status;
        this.user = user;
    }

    public String getNonce() {
        return nonce;
    }

    public String getCookie() {
        return cookie;
    }

    public boolean getValid() {
        return valid;
    }
   public String getStatus(){
        return status;
    }
    public User getUser(){
        return user;
    }

}
