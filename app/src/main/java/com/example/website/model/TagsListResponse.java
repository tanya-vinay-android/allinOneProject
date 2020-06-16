package com.example.website.model;

import java.util.List;

public class TagsListResponse {
    int count;
    List<TagsList> tags;

    public TagsListResponse(int count, List<TagsList> tags) {
        this.count = count;
        this.tags = tags;
    }

    public int getCount() {
        return count;
    }

    public List<TagsList> getTags() {
        return tags;
    }
}
