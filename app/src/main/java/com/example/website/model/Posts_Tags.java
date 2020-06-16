package com.example.website.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.SecureRandom;

public class Posts_Tags implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
