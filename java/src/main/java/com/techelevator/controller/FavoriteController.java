package com.techelevator.controller;

import com.techelevator.dao.FavoriteDao;
import com.techelevator.model.Favorite;
import com.techelevator.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FavoriteController {

    @Autowired
    private FavoriteDao dao;

    @RequestMapping(path="/favorites/{username}", method = RequestMethod.GET)
    public List<Post> getFavorites(@PathVariable String username){
            return dao.getFavoriteListOfPosts(username);
        }

    @RequestMapping(path="/favorites/{username}/ids", method = RequestMethod.GET)
    public List<Integer> getFavoriteIDs(@PathVariable String username){
        return dao.getFavoriteIds(username);
    }

    @RequestMapping(path = "/favorites/add", method = RequestMethod.POST)
    public void addFavorite(@RequestBody Favorite favorite){
        dao.addFavorite(favorite.getUsername(), favorite.getPostId());
        }

    @RequestMapping(path = "/favorites/{id}", method = RequestMethod.DELETE)
    public void removeFavorite(@PathVariable int id){
        dao.removeFavorite(id);
    }

    @RequestMapping(path="/favorites/{username}/photos", method = RequestMethod.GET)
    public List<String> getPhotosByUser(@PathVariable String username){
        return dao.getFavoritePhotos(username);
    }
}
