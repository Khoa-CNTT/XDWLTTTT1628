package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.response.GoalCompletionResponse;
import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.TestStatisticResponse;
import com.toeicstudyzone.dto.response.UserStatisticResponse;
import com.toeicstudyzone.service.AdminService;
import com.toeicstudyzone.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/summary")
public ResponseEntity<StatisticsResponse> getSummaryStats() {
    return ResponseEntity.ok(adminService.getUserStatistics());
}
    @GetMapping("/top-users")
    public ResponseEntity<List<UserStatisticResponse>> getTopUsers(
        @RequestParam int month,
        @RequestParam int year
    ) {
        return ResponseEntity.ok(statisticsService.getTopUsersByMonthYear(month, year));
    }

    @GetMapping("/top-tests")
    public ResponseEntity<List<TestStatisticResponse>> getTopTests() {
        return ResponseEntity.ok(statisticsService.getTopTestsByAttempt());
    }

    @GetMapping("/goal-completion")
    public ResponseEntity<GoalCompletionResponse> getGoalStats() {
        return ResponseEntity.ok(statisticsService.getGoalCompletionRate());
    }
}
