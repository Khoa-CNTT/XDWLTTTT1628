package com.toeicstudyzone.dto.request;

public class CommentRequest {
    private Long userId;
    private Long testId;
    private Long parentId;
    private String commentText;

    public CommentRequest() {
    }

    public CommentRequest(Long userId, Long testId, Long parentId, String commentText) {
        this.userId = userId;
        this.testId = testId;
        this.parentId = parentId;
        this.commentText = commentText;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}