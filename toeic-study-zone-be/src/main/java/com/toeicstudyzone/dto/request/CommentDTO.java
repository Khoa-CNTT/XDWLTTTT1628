package com.toeicstudyzone.dto.request;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long testId;
    private String testTitle;
    private Integer testYear;
    private Long parentId;
    private String commentText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String avatarUrl;

    public CommentDTO(Long id, Long userId, String userName, Long testId, String testTitle, Integer testYear,
            Long parentId, String commentText, LocalDateTime createdAt, LocalDateTime updatedAt, String status,
            String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.testId = testId;
        this.testTitle = testTitle;
        this.testYear = testYear;
        this.parentId = parentId;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public Integer getTestYear() {
        return testYear;
    }

    public void setTestYear(Integer testYear) {
        this.testYear = testYear;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}