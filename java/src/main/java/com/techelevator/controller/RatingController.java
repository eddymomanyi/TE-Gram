package com.techelevator.controller;

import com.techelevator.dao.RatingDao;
import com.techelevator.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RatingController {

    @Autowired
    private RatingDao dao;


    @RequestMapping(path = "/ratings", method = RequestMethod.GET)
    public List<Rating> getRatings() {
        return dao.getAllRatings();
    }

    @RequestMapping(path = "/ratings/{id}/{user}", method = RequestMethod.GET)
    public double getRatingByUser(@PathVariable int id, @PathVariable String user){
        return dao.getRatingByUser(id, user);
    }

    @RequestMapping(path = "/ratings/{id}/avg", method = RequestMethod.GET)
    public double getAvgRatingForPost(@PathVariable int id){
        return dao.getAvgRatingForPost(id);
    }

    @RequestMapping(path = "/ratings/{id}", method = RequestMethod.GET)
    public List<Rating> getRatingsByPost(@PathVariable int id) {
        return dao.getAllRatingsByPost(id);
    }

    @RequestMapping(path = "/ratings", method = RequestMethod.POST)
    public void addRating(@RequestBody Rating rating){
        dao.addRating(rating);
    }

    @RequestMapping(path = "/ratings", method = RequestMethod.PUT)
    public void updateRating(@RequestBody Rating rating){
        dao.updateRating(rating);
    }


}
