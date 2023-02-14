package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ManagePhotoController {

    @Autowired
    private PostDao dao;

    @RequestMapping(path="/post/{id}/like", method = RequestMethod.PUT)
    public void addLike(@PathVariable int id){
        dao.likePost(id);
    }

    @RequestMapping(path="/post/{id}/unlike", method = RequestMethod.PUT)
    public void removeLike(@PathVariable int id){
        dao.unLikePost(id);
    }

    @RequestMapping(path="/post/caption", method = RequestMethod.PUT)
    public void editCaption(@RequestBody Post post) {
        dao.editCaption(post);
    }

    @RequestMapping(path="/post/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable int id){
        dao.deletePost(id);
    }
}
