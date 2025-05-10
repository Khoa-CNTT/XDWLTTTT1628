package com.toeicstudyzone.dto.request;

  public class UserAnswerDTO {
      private Long questionId;
      private Long selectedOptionId;
      private Integer timeSpent;

      // Getters and Setters
      public Long getQuestionId() { return questionId; }
      public void setQuestionId(Long questionId) { this.questionId = questionId; }
      public Long getSelectedOptionId() { return selectedOptionId; }
      public void setSelectedOptionId(Long selectedOptionId) { this.selectedOptionId = selectedOptionId; }
      public Integer getTimeSpent() { return timeSpent; }
      public void setTimeSpent(Integer timeSpent) { this.timeSpent = timeSpent; }
  }