package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {

    @JsonProperty("comment_id")
    private int commentId;
    @JsonProperty("post_id")
    private int postId;
    private String commenter;
    private String comment;

    public Comment(int commentId, int postId, String commenter, String comment) {
        this.commentId = commentId;
        this.postId = postId;
        this.comment = comment;
        this.commenter = commenter;
    }

    public Comment() {

    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
