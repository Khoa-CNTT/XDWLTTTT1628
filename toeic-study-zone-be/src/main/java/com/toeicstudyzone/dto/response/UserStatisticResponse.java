package com.toeicstudyzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserStatisticResponse {
    private String fullName;
    private long totalAttempts;
}
