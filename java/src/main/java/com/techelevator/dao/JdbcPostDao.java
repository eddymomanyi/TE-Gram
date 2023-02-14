package com.techelevator.dao;


import com.techelevator.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcPostDao implements PostDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcPostDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Post> getPosts() {
        String sql = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while(result.next()) {
            Post post = mapRowToPost(result);
            posts.add(post);
        }

        return posts;
    }

    @Override
    public List<Post> getPostsByUsername(String username) {
        String sql = "SELECT * FROM posts WHERE username = ?" +
                "ORDER BY post_id DESC";

        List<Post> posts = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

        while(result.next()) {
            Post post = mapRowToPost(result);
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByRating(String order){
        String sortByHighestRating = "SELECT p.post_id as post_id, username, photo_url, likes, caption, date_time, COALESCE(AVG(rating), 0) as avg\n" +
                "FROM POSTS p LEFT JOIN ratings r ON p.post_id=r.post_id \n" +
                "GROUP BY p.post_id ORDER BY avg DESC";

        String sortByLowestRating = "SELECT p.post_id as post_id, username, photo_url, likes, caption, date_time, COALESCE(AVG(rating), 0) as avg\n" +
                "FROM POSTS p LEFT JOIN ratings r ON p.post_id=r.post_id \n" +
                "GROUP BY p.post_id ORDER BY avg ASC";

        String sql="";
        if(order.toLowerCase().equals("desc")){
            sql = sortByHighestRating;
        }else if(order.toLowerCase().equals("asc")){
            sql = sortByLowestRating;
        }

        List<Post> posts = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while(result.next()) {
            Post post = mapRowToPost(result);
            posts.add(post);
        }

        return posts;

    }

    @Override
    public List<Post> getPostsByTime(String order){
        String sortByLatestPosts = "SELECT p.post_id as post_id, username, photo_url, likes, caption, date_time\n" +
                "FROM POSTS p LEFT JOIN ratings r ON p.post_id=r.post_id \n" +
                "GROUP BY p.post_id ORDER BY date_time DESC";

        String sortByOldestPosts = "SELECT p.post_id as post_id, username, photo_url, likes, caption, date_time\n" +
                "FROM POSTS p LEFT JOIN ratings r ON p.post_id=r.post_id \n" +
                "GROUP BY p.post_id ORDER BY date_time ASC";

        String sql="";

        if(order.toLowerCase().equals("desc")){
            sql = sortByLatestPosts;
        }else if(order.toLowerCase().equals("asc")){
            sql = sortByOldestPosts;
        }

        List<Post> posts = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while(result.next()) {
            Post post = mapRowToPost(result);
            posts.add(post);
        }

        return posts;

    }


    private static Post mapRowToPost(SqlRowSet result){
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
    public Post getPost(int id) {
        String sql = "SELECT * FROM posts WHERE post_id=?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        Post post = new Post();
        if (result.next()) {
            post = mapRowToPost(result);
        }
        return post;
    }

    @Override
    public int createPost(Post post) {
        String sql = "INSERT INTO posts (username, photo_url, likes, caption, date_time) \n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING post_id";
        int postId;
        try{
            postId=jdbcTemplate.queryForObject(sql, Integer.class, post.getUsername(), post.getPhotoUrl(), post.getLikes(), post.getCaption(), post.getDateTime());
        } catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        return postId;

    }

    @Override
    public void likePost(int id) {
        String sql = "UPDATE posts \n" +
                "SET likes = (SELECT likes FROM posts WHERE post_id=?) + 1\n" +
                "WHERE post_id=?";
        jdbcTemplate.update(sql, id, id);
    }

    @Override
    public void unLikePost(int id) {
        String sql = "UPDATE posts \n" +
                "SET likes = (SELECT likes FROM posts WHERE post_id=?) - 1\n" +
                "WHERE post_id=?";
        jdbcTemplate.update(sql, id, id);
    }




    @Override
    public void editCaption(Post post) {
        String sql = "UPDATE posts " +
                "SET caption = ? " +
                "WHERE post_id = ?";
        jdbcTemplate.update(sql, post.getCaption() , post.getPostId());
    }

    @Override
    public void deletePost(int id) {
        String sql = "BEGIN TRANSACTION; " +
                "DELETE FROM comments " +
                "WHERE post_id = ?;" +
                "DELETE FROM favorites " +
                "WHERE post_id = ?;" +
                "DELETE FROM ratings " +
                "WHERE post_id = ?;" +
                "DELETE FROM posts " +
                "WHERE post_id = ?; " +
                "COMMIT TRANSACTION;";
        jdbcTemplate.update(sql, id, id, id, id);
    }

}
