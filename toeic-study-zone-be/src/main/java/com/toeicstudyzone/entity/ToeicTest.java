package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "toeic_tests")
public class ToeicTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "year_id", nullable = false)
    private TestYear testYear;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column(name = "is_free", nullable = false, columnDefinition = "boolean default false")
    private Boolean isFree;

    @Column(name = "is_published", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPublished;

    @Column(name = "is_placement_test", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPlacementTest;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "toeicTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestSection> sections;

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'testYear'
    public TestYear getTestYear() {
        return testYear;
    }

    public void setTestYear(TestYear testYear) {
        this.testYear = testYear;
    }

    // Getter and Setter for 'title'
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for 'totalQuestions'
    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    // Getter and Setter for 'timeLimit'
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    // Getter and Setter for 'isFree'
    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    // Getter and Setter for 'isPublished'
    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    // Getter and Setter for 'isPlacementTest'
    public Boolean getIsPlacementTest() {
        return isPlacementTest;
    }

    public void setIsPlacementTest(Boolean isPlacementTest) {
        this.isPlacementTest = isPlacementTest;
    }

    // Getter and Setter for 'createdAt'
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter and Setter for 'updatedAt'
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getter and Setter for 'sections'
    public List<TestSection> getSections() {
        return sections;
    }

    public void setSections(List<TestSection> sections) {
        this.sections = sections;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
