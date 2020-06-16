package com.example.website.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("pages")
    private int pages;
    @SerializedName("count_total")
    private int total_posts;
    @SerializedName("posts")
    private List<Posts> posts;

    public PostResponse(String status, int pages, int total_posts, List<Posts> posts) {
        this.status = status;
        this.pages = pages;
        this.total_posts = total_posts;
        this.posts = posts;
    }

    public String getStatus() {
        return status;
    }

    public int getPages() {
        return pages;
    }

    public int getTotal_posts() {
        return total_posts;
    }

    public List<Posts> getPosts() {
        return posts;
    }
}



