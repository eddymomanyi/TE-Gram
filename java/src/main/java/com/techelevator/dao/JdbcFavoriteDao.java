package com.techelevator.dao;

import com.techelevator.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcFavoriteDao implements FavoriteDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcFavoriteDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getFavoriteListOfPosts(String username) {
        String sql = "SELECT posts.post_id, posts.username, photo_url, likes, caption, date_time " +
                "FROM posts " +
                "JOIN favorites ON posts.post_id = favorites.post_id " +
                "WHERE favorites.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        List<Post> posts = new ArrayList<>();
        while(results.next()){
           Post post = mapRowToPost(results);
           posts.add(post);
        }

        return posts;
    }

    @Override
    public List<Integer> getFavoriteIds(String username) {
        String sql = "SELECT posts.post_id " +
                "FROM posts " +
                "JOIN favorites ON posts.post_id = favorites.post_id " +
                "WHERE favorites.username =?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        List<Integer> ids = new ArrayList<>();
        while(results.next()){
            int id = results.getInt("post_id");
            ids.add(id);
        }
        return ids;
    }

    private Post mapRowToPost(SqlRowSet result){
        Post post = new Post();
        post.setPostId(result.getInt("post_id"));
        post.setUsername(result.getString("username"));
        post.setPhotoUrl(result.getString("photo_url"));
        post.setLikes(result.getInt("likes"));
        post.setCaption(result.getString("caption"));
        if(result.getTimestamp("date_time") != null){
            post.setDateTime(result.getTimestamp("date_time").toLocalDateTime());
        }
        return post;

    }

    @Override
    public void addFavorite(String username, int postId) {
        String sql = "INSERT INTO favorites " +
                "(username, post_id) " +
                "VALUES (?,?)";
        jdbcTemplate.update(sql, username, postId);
    }

    @Override
    public void removeFavorite(int postId) {
        String sql = "DELETE FROM favorites " +
                "WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    public List<String> getFavoritePhotos(String username) {
        String sql = "SELECT photo_url FROM posts p JOIN favorites f ON p.post_id=f.post_id WHERE f.username=?";
        List<String> photoUrls = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

        while(result.next()){
            photoUrls.add(result.getString("photo_url"));
        }

        return photoUrls;
    }
}
