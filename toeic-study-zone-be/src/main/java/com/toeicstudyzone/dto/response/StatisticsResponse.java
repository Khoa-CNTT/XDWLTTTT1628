package com.toeicstudyzone.dto.response;

public class StatisticsResponse {

    private long totalUsers;
    private long totalTests;
    private long totalComments;
    private long totalGoals;
    private double averageScore;
    private long completedTests;

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


    public StatisticsResponse() {}

    public StatisticsResponse(long totalUsers, long totalTests, long totalComments, long totalGoals) {
        this.totalUsers = totalUsers;
        this.totalTests = totalTests;
        this.totalComments = totalComments;
        this.totalGoals = totalGoals;
    }

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

    public long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(long totalComments) {
        this.totalComments = totalComments;
    }

    public long getTotalGoals() {
        return totalGoals;
    }

    public void setTotalGoals(long totalGoals) {
        this.totalGoals = totalGoals;
    }
}
