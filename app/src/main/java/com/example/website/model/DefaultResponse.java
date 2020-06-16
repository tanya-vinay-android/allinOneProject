package com.example.website.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("nonce")
    private String nonce;
    @SerializedName("error")
    private String msg;

    public DefaultResponse(String  nonce,String msg) {
        this.nonce = nonce;
        this.msg=msg;
    }

    public String getNonce() {
        return nonce;
    }
    public String getMsg(){
        return msg;
    }


}
