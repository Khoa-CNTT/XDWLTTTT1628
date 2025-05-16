package com.toeicstudyzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TestStatisticResponse {
    private String testTitle;
    private int totalAttempts;
}
