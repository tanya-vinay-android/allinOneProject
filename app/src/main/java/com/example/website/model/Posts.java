package com.example.website.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Posts implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("excerpt")
    private String excerpt;

    @SerializedName("date")
    private String date;

    @SerializedName("tags")
    private List<Posts_Tags> tags;

    @SerializedName("author")
    private Posts_Author author;

    @SerializedName("thumbnail_images")
    private ThumbnailImage thumbnailImage;

    public Posts(int id, String url, String title, String content, String excerpt, String date, List<Posts_Tags> tags, Posts_Author author, ThumbnailImage thumbnailImage) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.date = date;
        this.tags = tags;
        this.author = author;
        this.thumbnailImage = thumbnailImage;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getDate() {
        return date;
    }

    public List<Posts_Tags> getTags() {
        return tags;
    }

    public Posts_Author getAuthor() {
        return author;
    }

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }
}



