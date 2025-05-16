package com.toeicstudyzone.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestHistoryResponse {
    private Long testId;
    private String testName;
    private Integer listeningScore;
    private Integer readingScore;
    private Integer totalScore;
    private LocalDateTime endTime;
}
