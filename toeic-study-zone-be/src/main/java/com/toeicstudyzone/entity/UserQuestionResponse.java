package com.toeicstudyzone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_question_responses")
public class UserQuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "history_id", nullable = false)
    private UserTestHistory userTestHistory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_answer_id")
    private AnswerOption selectedAnswer;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "time_spent")
    private Integer timeSpent;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserTestHistory getUserTestHistory() { return userTestHistory; }
    public void setUserTestHistory(UserTestHistory userTestHistory) { this.userTestHistory = userTestHistory; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public AnswerOption getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(AnswerOption selectedAnswer) { this.selectedAnswer = selectedAnswer; }
    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
    public Integer getTimeSpent() { return timeSpent; }
    public void setTimeSpent(Integer timeSpent) { this.timeSpent = timeSpent; }
}