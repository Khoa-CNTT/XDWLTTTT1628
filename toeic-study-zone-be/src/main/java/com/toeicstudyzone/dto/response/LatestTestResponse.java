package com.toeicstudyzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatestTestResponse {
    private Long id;
    private String title;
    private String description;
    private Integer timeLimit;
    private Integer totalQuestions;
    private Long participantsCount;
    private Long commentsCount;
}
