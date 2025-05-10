package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paragraphs")
public class Paragraph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passage_id", nullable = false)
    private Passage passage;

    @Column(name = "paragraph_text", nullable = false)
    private String content;

    private String paragraphTitle;

    @Column(nullable = false)
    private int paragraphOrder;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "paragraph", cascade = CascadeType.ALL)
    private ParagraphImage image;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Passage getPassage() { return passage; }
    public void setPassage(Passage passage) { this.passage = passage; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getParagraphTitle() { return paragraphTitle; }
    public void setParagraphTitle(String paragraphTitle) { this.paragraphTitle = paragraphTitle; }
    public int getParagraphOrder() { return paragraphOrder; }
    public void setParagraphOrder(int paragraphOrder) { this.paragraphOrder = paragraphOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public ParagraphImage getImage() { return image; }
    public void setImage(ParagraphImage image) { this.image = image; }
}