package com.toeicstudyzone.dto.response;

import java.util.List;

public class ToeicTestResponse {
    private Long id;
    private String title;
    private int totalQuestions;
    private int timeLimit;
    private List<TestSectionResponse> sections;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    public List<TestSectionResponse> getSections() { return sections; }
    public void setSections(List<TestSectionResponse> sections) { this.sections = sections; }
}