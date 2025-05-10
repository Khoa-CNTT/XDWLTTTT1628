package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_tests_taken")
    private Integer totalTestsTaken;

    @Column(name = "total_time_spent")
    private Integer totalTimeSpent;

    @Column(name = "average_score")
    private Integer averageScore;

    @Column(name = "listening_overall")
    private BigDecimal listeningOverall;

    @Column(name = "listening_part1")
    private BigDecimal listeningPart1;

    @Column(name = "listening_part2")
    private BigDecimal listeningPart2;

    @Column(name = "listening_part3")
    private BigDecimal listeningPart3;

    @Column(name = "listening_part4")
    private BigDecimal listeningPart4;

    @Column(name = "reading_overall")
    private BigDecimal readingOverall;

    @Column(name = "reading_part5")
    private BigDecimal readingPart5;

    @Column(name = "reading_part6")
    private BigDecimal readingPart6;

    @Column(name = "reading_part7")
    private BigDecimal readingPart7;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (totalTestsTaken == null) {
            totalTestsTaken = 0;
        }
        if (totalTimeSpent == null) {
            totalTimeSpent = 0;
        }
        if (averageScore == null) {
            averageScore = 0;
        }
        if (listeningOverall == null) {
            listeningOverall = BigDecimal.ZERO;
        }
        if (listeningPart1 == null) {
            listeningPart1 = BigDecimal.ZERO;
        }
        if (listeningPart2 == null) {
            listeningPart2 = BigDecimal.ZERO;
        }
        if (listeningPart3 == null) {
            listeningPart3 = BigDecimal.ZERO;
        }
        if (listeningPart4 == null) {
            listeningPart4 = BigDecimal.ZERO;
        }
        if (readingOverall == null) {
            readingOverall = BigDecimal.ZERO;
        }
        if (readingPart5 == null) {
            readingPart5 = BigDecimal.ZERO;
        }
        if (readingPart6 == null) {
            readingPart6 = BigDecimal.ZERO;
        }
        if (readingPart7 == null) {
            readingPart7 = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}