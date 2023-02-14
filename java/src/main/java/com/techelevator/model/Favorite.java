package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Favorite {

    private String username;
    @JsonProperty("post_id")
    private int postId;

    public Favorite(String username, int postId) {
        this.username = username;
        this.postId = postId;
    }

    public Favorite() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
