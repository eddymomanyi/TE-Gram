package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.PostDao;
import com.techelevator.model.Comment;
import com.techelevator.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class PhotoFeedController {

    @Autowired
    private PostDao dao;
    @Autowired
    private CommentDao commentDao;

    @RequestMapping(path="/posts", method = RequestMethod.GET)
    public List<Post> getPosts(){
        return dao.getPosts();
    }

    @RequestMapping(path="/posts/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable int id){
        return dao.getPost(id);
    }

    @RequestMapping(path="/post/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getComments(@PathVariable int id){
        return commentDao.getCommentsByPost(id);
    }

    @RequestMapping(path="/comments", method = RequestMethod.GET)
    public List<Comment> getAllComments(){
        return commentDao.getAllComments();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/post/create-comment", method = RequestMethod.POST)
    public int createNewComment(@RequestBody Comment comment) {
        return commentDao.createNewComment(comment);
    }

    @RequestMapping(path="/posts/create", method= RequestMethod.POST)
    public int createPost(@RequestBody Post post){
        LocalDateTime dateTime = LocalDateTime.now();
        post.setDateTime(dateTime);
        return dao.createPost(post);
    }

    @RequestMapping(path="/posts/rating/{order}", method = RequestMethod.GET)
    public List<Post> getPostsByRating(@PathVariable String order){
        return dao.getPostsByRating(order);
    }

    @RequestMapping(path="/posts/time/{order}", method = RequestMethod.GET)
    public List<Post> getPostsByTime(@PathVariable String order){
        return dao.getPostsByTime(order);
    }
}
