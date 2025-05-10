package com.toeicstudyzone.dto.request;

import java.util.List;

public class SubmitTestRequest {
    private Long toeicTestId;
    private Long userId;
    private List<UserAnswerDTO> answers;

    // Getters and Setters
    public Long getToeicTestId() {
        return toeicTestId;
    }

    public void setToeicTestId(Long toeicTestId) {
        this.toeicTestId = toeicTestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UserAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerDTO> answers) {
        this.answers = answers;
    }
}
