package com.example.website.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Posts_Author implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
