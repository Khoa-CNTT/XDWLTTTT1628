package com.toeicstudyzone.dto.response;

import com.toeicstudyzone.enums.StudyCategory;
import com.toeicstudyzone.enums.StudyScheduleType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudyScheduleResponse {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StudyScheduleItemResponse> items;

    @Data
    public static class StudyScheduleItemResponse {
        private Long id;
        private String title;
        private StudyCategory category;
        private StudyScheduleType type;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}