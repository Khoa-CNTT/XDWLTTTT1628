package com.toeicstudyzone.dto.request;

import java.time.LocalDateTime;

public class UserTestHistoryDTO {
    private Long id;
    private Long userId;
    private Long testId;
    private String testTitle;
    private Integer testYear;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer totalScore;
    private Integer listeningScore;
    private Integer readingScore;
    private Integer correctAnswers;
    private Boolean completed;

    // Constructor
    public UserTestHistoryDTO(Long id, Long userId, Long testId, String testTitle, Integer testYear,
                              LocalDateTime startTime, LocalDateTime endTime, Integer totalScore,
                              Integer listeningScore, Integer readingScore, Integer correctAnswers,
                              Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.testId = testId;
        this.testTitle = testTitle;
        this.testYear = testYear;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalScore = totalScore;
        this.listeningScore = listeningScore;
        this.readingScore = readingScore;
        this.correctAnswers = correctAnswers;
        this.completed = completed;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }
    public String getTestTitle() { return testTitle; }
    public void setTestTitle(String testTitle) { this.testTitle = testTitle; }
    public Integer getTestYear() { return testYear; }
    public void setTestYear(Integer testYear) { this.testYear = testYear; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public Integer getListeningScore() { return listeningScore; }
    public void setListeningScore(Integer listeningScore) { this.listeningScore = listeningScore; }
    public Integer getReadingScore() { return readingScore; }
    public void setReadingScore(Integer readingScore) { this.readingScore = readingScore; }
    public Integer getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(Integer correctAnswers) { this.correctAnswers = correctAnswers; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}