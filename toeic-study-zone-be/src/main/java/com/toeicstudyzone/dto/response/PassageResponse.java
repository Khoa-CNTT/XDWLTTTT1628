package com.toeicstudyzone.dto.response;

import com.toeicstudyzone.enums.PassageType;

import java.util.List;

public class PassageResponse {
    private String title;
    private PassageType passageType;
    private int passageOrder;
    private List<ParagraphResponse> paragraphs;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PassageType getPassageType() {
        return passageType;
    }

    public void setPassageType(PassageType passageType) {
        this.passageType = passageType;
    }

    public int getPassageOrder() {
        return passageOrder;
    }

    public void setPassageOrder(int passageOrder) {
        this.passageOrder = passageOrder;
    }

    public List<ParagraphResponse> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<ParagraphResponse> paragraphs) {
        this.paragraphs = paragraphs;
    }
}