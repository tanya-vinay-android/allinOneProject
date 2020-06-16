package com.example.website.model;

import com.example.website.model.Rendered;

public class AnswerResponse {
    private int id;
    private String date_gmt;
    private Rendered content;
    int author;

    public AnswerResponse(int id, String date_gmt, Rendered content, int author) {
        this.id = id;
        this.date_gmt = date_gmt;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getDate_gmt() {
        return date_gmt;
    }

    public Rendered getContent() {
        return content;
    }

    public int getAuthor() {
        return author;
    }
}
