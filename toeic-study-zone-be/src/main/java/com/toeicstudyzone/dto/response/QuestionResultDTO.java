package com.toeicstudyzone.dto.response;

public class QuestionResultDTO {
    private Long questionId;
    private Long correctAnswerId;
    private Long selectedAnswerId;
    private boolean isCorrect;

    // Getters and Setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public Long getCorrectAnswerId() { return correctAnswerId; }
    public void setCorrectAnswerId(Long correctAnswerId) { this.correctAnswerId = correctAnswerId; }
    public Long getSelectedAnswerId() { return selectedAnswerId; }
    public void setSelectedAnswerId(Long selectedAnswerId) { this.selectedAnswerId = selectedAnswerId; }
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean isCorrect) { this.isCorrect = isCorrect; }
}