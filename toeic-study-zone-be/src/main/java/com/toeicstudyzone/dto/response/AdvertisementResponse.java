package com.toeicstudyzone.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AdvertisementResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String linkUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}