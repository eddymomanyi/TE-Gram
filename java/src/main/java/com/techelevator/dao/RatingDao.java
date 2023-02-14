package com.techelevator.dao;

import com.techelevator.model.Rating;

import java.util.List;

public interface RatingDao {
    public List<Rating> getAllRatings();

    public double getAvgRatingForPost(int post_id);

    List<Rating> getAllRatingsByPost(int post_id);

    public double getRatingByUser(int post_id, String user);

    public void addRating(Rating rating);

    public void updateRating(Rating rating);
}
