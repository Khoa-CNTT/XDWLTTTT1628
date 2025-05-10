package com.toeicstudyzone.dto.request;

import com.toeicstudyzone.enums.StudyScheduleType;
import com.toeicstudyzone.enums.StudyCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudyScheduleRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private Boolean isCompleted;

    private List<StudyScheduleItemRequest> items;

    @Data
    public static class StudyScheduleItemRequest {
        @NotBlank(message = "Title is required")
        private String title;

        private StudyCategory category;

        @NotNull(message = "Type is required")
        private StudyScheduleType type;
    }
}