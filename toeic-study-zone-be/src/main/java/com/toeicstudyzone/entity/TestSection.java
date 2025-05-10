package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_sections")
public class TestSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private ToeicTest toeicTest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionType sectionType;

    @Column(nullable = false)
    private int sectionPart;

    private String title;

    private String instructions;

    @OneToMany(mappedBy = "testSection", cascade = CascadeType.ALL)
    private List<Passage> passages;

    @OneToMany(mappedBy = "testSection", cascade = CascadeType.ALL)
    private List<Question> questions;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ToeicTest getToeicTest() { return toeicTest; }
    public void setToeicTest(ToeicTest toeicTest) { this.toeicTest = toeicTest; }
    public SectionType getSectionType() { return sectionType; }
    public void setSectionType(SectionType sectionType) { this.sectionType = sectionType; }
    public int getSectionPart() { return sectionPart; }
    public void setSectionPart(int sectionPart) { this.sectionPart = sectionPart; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public List<Passage> getPassages() { return passages; }
    public void setPassages(List<Passage> passages) { this.passages = passages; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public enum SectionType {
        LISTENING, READING
    }
}