package com.techelevator.dao;

import com.techelevator.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcCommentDao implements CommentDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcCommentDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> getAllComments() {
        String sql = "SELECT * FROM comments " +
                "ORDER BY comment_id";
        List<Comment> comments = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while(result.next()){
            Comment comment = mapRowToComment(result);
            comments.add(comment);
        }
        System.out.println(comments);
        return comments;
    }

    @Override
    public List<Comment> getCommentsByPost(int postId) {
        String sql = "SELECT * FROM comments WHERE post_id=?";
        List<Comment> comments = new ArrayList<>();

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, postId);

        while(result.next()){
            Comment comment = mapRowToComment(result);
            comments.add(comment);
        }
        System.out.println(comments);
        return comments;
    }

    @Override
    public int createNewComment(Comment comment) {
        String sql = "INSERT INTO comments (post_id, commenter, comment)\n" +
                "VALUES (?, ?, ?) RETURNING comment_id";

        int commentId;

        try {
            commentId = jdbcTemplate.queryForObject(sql, Integer.class, comment.getPostId(), comment.getCommenter(), comment.getComment());
        } catch (NullPointerException e) {
            commentId = -1;
        }

        return commentId;
    }

    private static Comment mapRowToComment(SqlRowSet result){
        Comment comment = new Comment();

        comment.setCommentId(result.getInt("comment_id"));
        comment.setPostId(result.getInt("post_id"));
        comment.setCommenter(result.getString("commenter"));
        comment.setComment(result.getString("comment"));

        return comment;
    }


}
