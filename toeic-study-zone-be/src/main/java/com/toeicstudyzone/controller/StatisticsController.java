package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        return error;
    }

    @GetMapping
    public ResponseEntity<?> getStatistics() {
        try {
            StatisticsResponse stats = statisticsService.getStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(createErrorResponse("Error retrieving statistics: " + e.getMessage()));
        }
    }
}