package com.toeicstudyzone.dto.response;

import java.util.List;

public class TestResultResponse {
    private Integer totalScore;
    private Integer listeningScore;
    private Integer readingScore;
    private int correctCount;
    private double percentage;
    private List<QuestionResultDTO> results;

    // Getters and Setters
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public Integer getListeningScore() { return listeningScore; }
    public void setListeningScore(Integer listeningScore) { this.listeningScore = listeningScore; }
    public Integer getReadingScore() { return readingScore; }
    public void setReadingScore(Integer readingScore) { this.readingScore = readingScore; }
    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public List<QuestionResultDTO> getResults() { return results; }
    public void setResults(List<QuestionResultDTO> results) { this.results = results; }
}