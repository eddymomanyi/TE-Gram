package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post {

    @JsonProperty("post_id")
    private int postId;
    private String username;
    @JsonProperty("photo_url")
    private String photoUrl;
    private int likes;
    private String caption;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    public Post(int postId, String username, String photoUrl, int likes, String caption, LocalDateTime dateTime) {
        this.postId = postId;
        this.username = username;
        this.photoUrl = photoUrl;
        this.likes = likes;
        this.caption = caption;
        this.dateTime = dateTime;
    }

    public Post(){

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
