package com.toeicstudyzone.dto.request;

import java.time.LocalDateTime;

public class ToeicTestDTO {
    private Long id;
    private String title;
    private Integer yearId;
    private String description;
    private Integer totalQuestions;
    private Integer timeLimit;
    private Boolean isFree;
    private Boolean isPublished;
    private Boolean isPlacementTest;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long commentsCount;
    private Long participantsCount;

    public ToeicTestDTO() {
    }

    public ToeicTestDTO(Long id, String title, Integer yearId, String description, Integer totalQuestions,
            Integer timeLimit, Boolean isFree, Boolean isPublished, Boolean isPlacementTest,
            LocalDateTime createdAt, LocalDateTime updatedAt,
            Long participantsCount, Long commentsCount) {
        this.id = id;
        this.title = title;
        this.yearId = yearId;
        this.description = description;
        this.totalQuestions = totalQuestions;
        this.timeLimit = timeLimit;
        this.isFree = isFree;
        this.isPublished = isPublished;
        this.isPlacementTest = isPlacementTest;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.participantsCount = participantsCount;
        this.commentsCount = commentsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsPlacementTest() {
        return isPlacementTest;
    }

    public void setIsPlacementTest(Boolean isPlacementTest) {
        this.isPlacementTest = isPlacementTest;
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

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Long getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Long participantsCount) {
        this.participantsCount = participantsCount;
    }

}
