package com.example.website.model;

import android.media.MediaPlayer;

import java.io.Serializable;

public class ThumbnailImage implements Serializable {
    Medium full;

    public ThumbnailImage(Medium full) {
        this.full = full;
    }

    public Medium getFull() {
        return full;
    }
}
