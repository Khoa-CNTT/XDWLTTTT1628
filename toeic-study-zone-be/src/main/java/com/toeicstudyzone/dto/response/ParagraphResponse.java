package com.toeicstudyzone.dto.response;

public class ParagraphResponse {
    private String content;
    private String paragraphTitle;
    private int paragraphOrder;
    private String imageUrl;
    private int imageOrder;

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParagraphTitle() {
        return paragraphTitle;
    }

    public void setParagraphTitle(String paragraphTitle) {
        this.paragraphTitle = paragraphTitle;
    }

    public int getParagraphOrder() {
        return paragraphOrder;
    }

    public void setParagraphOrder(int paragraphOrder) {
        this.paragraphOrder = paragraphOrder;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }
}