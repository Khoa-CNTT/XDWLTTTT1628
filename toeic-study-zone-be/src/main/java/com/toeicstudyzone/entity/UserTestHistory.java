package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_test_history")
public class UserTestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id", nullable = false)
    private ToeicTest test;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "listening_score")
    private Integer listeningScore;

    @Column(name = "reading_score")
    private Integer readingScore;

    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Column(nullable = false)
    private Boolean completed;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private Mode mode;

    @Column(name = "part_number")
    private Integer partNumber;

    @PrePersist
    protected void onCreate() {
        startTime = LocalDateTime.now();
        if (correctAnswers == null) {
            correctAnswers = 0;
        }
        if (completed == null) {
            completed = false;
        }
    }

    public enum Mode {
        FULL_TEST,
        PART_TEST
    }
}