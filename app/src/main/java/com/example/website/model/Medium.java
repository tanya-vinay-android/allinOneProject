package com.example.website.model;

import java.io.Serializable;

public class Medium implements Serializable {
    String url;

    public Medium(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
