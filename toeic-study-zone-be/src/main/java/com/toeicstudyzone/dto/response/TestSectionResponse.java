package com.toeicstudyzone.dto.response;

import com.toeicstudyzone.entity.TestSection;

import java.util.List;

public class TestSectionResponse {
    private TestSection.SectionType sectionType;
    private int sectionPart;
    private String title;
    private String instructions;
    private List<PassageResponse> passages;
    private List<QuestionResponse> questions;

    // Getters and Setters
    public TestSection.SectionType getSectionType() { return sectionType; }
    public void setSectionType(TestSection.SectionType sectionType) { this.sectionType = sectionType; }
    public int getSectionPart() { return sectionPart; }
    public void setSectionPart(int sectionPart) { this.sectionPart = sectionPart; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public List<PassageResponse> getPassages() { return passages; }
    public void setPassages(List<PassageResponse> passages) { this.passages = passages; }
    public List<QuestionResponse> getQuestions() { return questions; }
    public void setQuestions(List<QuestionResponse> questions) { this.questions = questions; }
}