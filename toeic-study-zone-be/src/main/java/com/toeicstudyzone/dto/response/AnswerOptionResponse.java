package com.toeicstudyzone.dto.response;

public class AnswerOptionResponse {
    private Long id;
    private String content;
    private char optionLabel;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public char getOptionLabel() { return optionLabel; }
    public void setOptionLabel(char optionLabel) { this.optionLabel = optionLabel; }
}