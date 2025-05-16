package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.response.GoalCompletionResponse;
import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.TestStatisticResponse;
import com.toeicstudyzone.dto.response.UserStatisticResponse;

import java.util.List;

public interface StatisticsService {
    List<UserStatisticResponse> getTopUsersByMonthYear(int month, int year);
    List<TestStatisticResponse> getTopTestsByAttempt();
    GoalCompletionResponse getGoalCompletionRate();
    StatisticsResponse getStatistics();
}