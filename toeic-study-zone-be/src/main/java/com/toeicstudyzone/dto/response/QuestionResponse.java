package com.toeicstudyzone.dto.response;

import java.util.List;
import com.toeicstudyzone.enums.QuestionType;


public class QuestionResponse {
    private Long id;
    private int questionNumber;
    private String content;
    private QuestionType questionType;
    private String audioUrl;
    private String imageUrl;
    private List<AnswerOptionResponse> answerOptions;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public QuestionType getQuestionType() { return questionType; }
    public void setQuestionType(QuestionType questionType) { this.questionType = questionType; }
    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<AnswerOptionResponse> getAnswerOptions() { return answerOptions; }
    public void setAnswerOptions(List<AnswerOptionResponse> answerOptions) { this.answerOptions = answerOptions; }
}