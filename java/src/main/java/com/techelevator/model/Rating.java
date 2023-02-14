package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

    @JsonProperty("rating_id")
    private int ratingId;
    @JsonProperty("post_id")
    private int postId;
    private double rating;
    private String rater;

    public Rating(int ratingId, int postId, double rating, String rater) {
        this.ratingId = ratingId;
        this.postId = postId;
        this.rating = rating;
        this.rater = rater;
    }

    public Rating() {

    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRater() {
        return rater;
    }

    public void setRater(String rater) {
        this.rater = rater;
    }
}
