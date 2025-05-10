package com.toeicstudyzone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "paragraph_images")
public class ParagraphImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paragraph_id", nullable = false)
    private Paragraph paragraph;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int imageOrder;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paragraph getParagraph() { return paragraph; }
    public void setParagraph(Paragraph paragraph) { this.paragraph = paragraph; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getImageOrder() { return imageOrder; }
    public void setImageOrder(int imageOrder) { this.imageOrder = imageOrder; }
}