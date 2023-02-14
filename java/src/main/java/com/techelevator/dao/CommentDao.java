package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    public List<Comment> getAllComments();

    public List<Comment> getCommentsByPost(int postId);

    public int createNewComment(Comment comment);
}
