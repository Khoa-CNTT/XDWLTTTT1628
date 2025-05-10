package com.toeicstudyzone.entity;

import com.toeicstudyzone.enums.PassageType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "passages")
public class Passage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private TestSection testSection;

    @Column(length = 255)
    private String title;

    @Enumerated(EnumType.STRING)    
    private PassageType passageType = PassageType.SINGLE;

    @Column(nullable = false)
    private int passageOrder;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "passage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Paragraph> paragraphs;

    @OneToMany(mappedBy = "passage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestSection getTestSection() { return testSection; }
    public void setTestSection(TestSection testSection) { this.testSection = testSection; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public PassageType getPassageType() { return passageType; }
    public void setPassageType(PassageType passageType) { this.passageType = passageType; }
    public int getPassageOrder() { return passageOrder; }
    public void setPassageOrder(int passageOrder) { this.passageOrder = passageOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<Paragraph> getParagraphs() { return paragraphs; }
    public void setParagraphs(List<Paragraph> paragraphs) { this.paragraphs = paragraphs; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}