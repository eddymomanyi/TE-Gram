package com.techelevator.dao;

import com.techelevator.model.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcRatingDao implements RatingDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcRatingDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rating> getAllRatings() {
        String sql = "SELECT rating_id, post_id, rating, rater " +
                "FROM ratings";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        List<Rating> ratings = new ArrayList<>();
        while(results.next()){
            Rating rating = mapRowToRating(results);
            ratings.add(rating);
        }
        return ratings;
    }

    @Override
    public double getAvgRatingForPost(int post_id) {
        String sql = "SELECT AVG(rating) AS avg " +
                "FROM ratings " +
                "WHERE post_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, post_id);
        double rating = -1;
        if (result.next()) {
            rating = result.getDouble("avg");
        }
        return rating;
    }

    @Override
    public List<Rating> getAllRatingsByPost(int postId) {
        String sql = "SELECT rating_id, post_id, rating, rater " +
                "FROM ratings " +
                "WHERE post_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, postId);
        List<Rating> ratings = new ArrayList<>();
        while(results.next()){
            Rating rating = mapRowToRating(results);
            ratings.add(rating);
        }
        return ratings;
    }

    @Override
    public double getRatingByUser(int post_id, String user) {
        String sql = "SELECT rating " +
                "FROM ratings " +
                "WHERE post_id = ? AND rater = ? ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, post_id, user);
        double rating = -1;
        if(result.next()) {
            rating = result.getDouble("rating");
        }
        return rating;
    }

    @Override
    public void addRating(Rating rating) {
        String sql = "INSERT INTO ratings " +
                "(post_id, rating, rater) " +
                "VALUES (?, ?, ?)";


       jdbcTemplate.update(sql, rating.getPostId(), rating.getRating(), rating.getRater());

    }

    @Override
    public void updateRating(Rating rating) {
        String sql = "UPDATE ratings " +
                "SET rating = ? " +
                "WHERE rater = ? AND post_id = ?";
        jdbcTemplate.update(sql, rating.getRating(), rating.getRater(), rating.getPostId());

    }

    private Rating mapRowToRating(SqlRowSet results) {
        Rating rating = new Rating();
        rating.setRatingId(results.getInt("rating_id"));
        rating.setPostId(results.getInt("post_id"));
        rating.setRating(results.getDouble("rating"));
        rating.setRater(results.getString("rater"));
        return rating;
    }
}
