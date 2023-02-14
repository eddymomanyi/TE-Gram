package com.techelevator.dao;

import com.techelevator.model.Post;

import java.util.List;

public interface FavoriteDao {

    public List<Post> getFavoriteListOfPosts(String username);

    public List<Integer> getFavoriteIds(String username);

    public void addFavorite(String username, int postId);

    public void removeFavorite(int postId);

    public List<String> getFavoritePhotos(String username);
}
