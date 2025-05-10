package com.toeicstudyzone.entity;

import com.toeicstudyzone.enums.QuestionType;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private TestSection testSection;

    @ManyToOne
    @JoinColumn(name = "passage_id")
    private Passage passage;

    @Column(nullable = false)
    private int questionNumber;

    @Column(name = "question_text", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType = QuestionType.MULTIPLE_CHOICE;

    private String audioUrl;

    private String imageUrl;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerOption> answerOptions;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestSection getTestSection() { return testSection; }
    public void setTestSection(TestSection testSection) { this.testSection = testSection; }
    public Passage getPassage() { return passage; }
    public void setPassage(Passage passage) { this.passage = passage; }
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
    public List<AnswerOption> getAnswerOptions() { return answerOptions; }
    public void setAnswerOptions(List<AnswerOption> answerOptions) { this.answerOptions = answerOptions; }
}