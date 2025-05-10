package com.toeicstudyzone.dto.response;

public class StatisticsResponse {
    private long totalUsers;
    private long totalTests;
    private long completedTests;
    private double averageScore;
    private long totalComments;

    // Constructor
    public StatisticsResponse(long totalUsers, long totalTests, long completedTests, double averageScore, long totalComments) {
        this.totalUsers = totalUsers;
        this.totalTests = totalTests;
        this.completedTests = completedTests;
        this.averageScore = averageScore;
        this.totalComments = totalComments;
    }

    // Getters and Setters
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(long totalTests) {
        this.totalTests = totalTests;
    }

    public long getCompletedTests() {
        return completedTests;
    }

    public void setCompletedTests(long completedTests) {
        this.completedTests = completedTests;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(long totalComments) {
        this.totalComments = totalComments;
    }
}