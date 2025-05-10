package com.toeicstudyzone.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_years")
public class TestYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int year;

    @OneToMany(mappedBy = "testYear", cascade = CascadeType.ALL)
    private List<ToeicTest> toeicTests;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public List<ToeicTest> getToeicTests() { return toeicTests; }
    public void setToeicTests(List<ToeicTest> toeicTests) { this.toeicTests = toeicTests; }
}