package com.example.website.model;

import java.util.List;

public class QuestionResponse {
    private int id;
    private String date_gmt;
    private Rendered title;
    private Rendered content;
    private int author;
    private List<Integer> question_tag;
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public QuestionResponse(int id, String date_gmt, Rendered title, Rendered content, int author, List<Integer> question_tag) {
        this.id = id;
        this.date_gmt = date_gmt;
        this.title = title;
        this.content = content;
        this.author = author;
        this.question_tag = question_tag;
        this.expanded = false;
    }

    public int getId() {
        return id;
    }

    public String getDate_gmt() {
        return date_gmt;
    }

    public Rendered getTitle() {
        return title;
    }

    public Rendered getContent() {
        return content;
    }

    public int getAuthor() {
        return author;
    }

    public List<Integer> getQuestion_tag() {
        return question_tag;
    }
}
