package com.toeicstudyzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class GoalCompletionResponse {
    private long totalGoals;
    private long completedGoals;
    private double completionRate;
}
