package com.techelevator.dao;

import com.techelevator.model.Post;

import java.util.List;

public interface PostDao {

    public List<Post> getPosts();

    public List<Post> getPostsByUsername(String username);

    public Post getPost(int id);

    public int createPost(Post post);

    public void likePost(int id);

    public void unLikePost(int id);

    public List<Post> getPostsByTime(String order);

    public List<Post> getPostsByRating(String order);

    public void editCaption(Post post);

    public void deletePost(int id);

}
