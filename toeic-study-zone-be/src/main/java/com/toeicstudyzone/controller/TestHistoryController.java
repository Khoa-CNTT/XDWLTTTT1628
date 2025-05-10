package com.toeicstudyzone.controller;

import com.toeicstudyzone.entity.UserTestHistory;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.ToeicTest;
import com.toeicstudyzone.service.TestHistoryService;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.ToeicTestRepository;
import com.toeicstudyzone.repository.UserTestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/test-history")
public class TestHistoryController {

    @Autowired
    private TestHistoryService testHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        return error;
    }

    @PostMapping
    public ResponseEntity<?> createTestHistory(@RequestBody Map<String, Object> requestBody) {
        try {
            // Tạo đối tượng UserTestHistory
            UserTestHistory testHistory = new UserTestHistory();

            // Xử lý user
            Object userObj = requestBody.get("user");
            if (userObj == null || !(userObj instanceof Map)) {
                return ResponseEntity.status(400).body(createErrorResponse("User must be a non-null object"));
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> userMap = (Map<String, Object>) userObj;
            if (userMap.get("id") == null) {
                return ResponseEntity.status(400).body(createErrorResponse("User ID is required"));
            }
            Long userId;
            try {
                userId = Long.valueOf(userMap.get("id").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.status(400).body(createErrorResponse("Invalid User ID format"));
            }
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(404).body(createErrorResponse("User with ID " + userId + " not found"));
            }
            testHistory.setUser(userOptional.get());

            // Xử lý test
            Object testObj = requestBody.get("test");
            if (testObj == null || !(testObj instanceof Map)) {
                return ResponseEntity.status(400).body(createErrorResponse("Test must be a non-null object"));
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> testMap = (Map<String, Object>) testObj;
            if (testMap.get("id") == null) {
                return ResponseEntity.status(400).body(createErrorResponse("Test ID is required"));
            }
            Long testId;
            try {
                testId = Long.valueOf(testMap.get("id").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.status(400).body(createErrorResponse("Invalid Test ID format"));
            }
            Optional<ToeicTest> testOptional = toeicTestRepository.findById(testId);
            if (!testOptional.isPresent()) {
                return ResponseEntity.status(404).body(createErrorResponse("Test with ID " + testId + " not found"));
            }
            testHistory.setTest(testOptional.get());

            // Xử lý score (map vào totalScore)
            Integer totalScore = 0;
            Integer listeningScore = 0;
            Integer readingScore = 0;
            if (requestBody.containsKey("score")) {
                try {
                    totalScore = Integer.valueOf(requestBody.get("score").toString());
                    if (totalScore < 0) {
                        return ResponseEntity.status(400).body(createErrorResponse("Total score cannot be negative"));
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(400).body(createErrorResponse("Invalid score format"));
                }
                // Gán mặc định cho listeningScore và readingScore nếu không có
                if (requestBody.containsKey("listeningScore")) {
                    try {
                        listeningScore = Integer.valueOf(requestBody.get("listeningScore").toString());
                        if (listeningScore < 0) {
                            return ResponseEntity.status(400).body(createErrorResponse("Listening score cannot be negative"));
                        }
                    } catch (NumberFormatException e) {
                        return ResponseEntity.status(400).body(createErrorResponse("Invalid listeningScore format"));
                    }
                } else {
                    listeningScore = totalScore / 2;
                }
                if (requestBody.containsKey("readingScore")) {
                    try {
                        readingScore = Integer.valueOf(requestBody.get("readingScore").toString());
                        if (readingScore < 0) {
                            return ResponseEntity.status(400).body(createErrorResponse("Reading score cannot be negative"));
                        }
                    } catch (NumberFormatException e) {
                        return ResponseEntity.status(400).body(createErrorResponse("Invalid readingScore format"));
                    }
                } else {
                    readingScore = totalScore / 2;
                }
            }
            testHistory.setTotalScore(totalScore);
            testHistory.setListeningScore(listeningScore);
            testHistory.setReadingScore(readingScore);

            // Xử lý các trường khác nếu có
            if (requestBody.containsKey("correctAnswers")) {
                try {
                    int correctAnswers = Integer.valueOf(requestBody.get("correctAnswers").toString());
                    if (correctAnswers < 0) {
                        return ResponseEntity.status(400).body(createErrorResponse("Correct answers cannot be negative"));
                    }
                    testHistory.setCorrectAnswers(correctAnswers);
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(400).body(createErrorResponse("Invalid correctAnswers format"));
                }
            }

            if (requestBody.containsKey("completed")) {
                try {
                    testHistory.setCompleted(Boolean.valueOf(requestBody.get("completed").toString()));
                } catch (Exception e) {
                    return ResponseEntity.status(400).body(createErrorResponse("Invalid completed format"));
                }
            }

            if (requestBody.containsKey("endTime")) {
                try {
                    testHistory.setEndTime(LocalDateTime.parse(requestBody.get("endTime").toString()));
                } catch (Exception e) {
                    return ResponseEntity.status(400).body(createErrorResponse("Invalid endTime format"));
                }
            }

            // Gán startTime nếu không có (không bắt buộc vì @PrePersist đã xử lý)
            if (testHistory.getStartTime() == null) {
                testHistory.setStartTime(LocalDateTime.now());
            }

            // Lưu qua service
            UserTestHistory savedHistory = testHistoryService.saveHistory(testHistory);
            return ResponseEntity.ok(savedHistory);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(createErrorResponse("Error saving test history: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getHistoryByUser(@PathVariable Long userId) {
        List<UserTestHistory> history = userTestHistoryRepository.findByUserId(userId);
        if (history.isEmpty()) {
            return ResponseEntity.status(404).body(createErrorResponse("No history found for user ID: " + userId));
        }
        return ResponseEntity.ok(history);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<?> getHistoryByTest(@PathVariable Long testId) {
        List<UserTestHistory> history = userTestHistoryRepository.findByTestId(testId);
        if (history.isEmpty()) {
            return ResponseEntity.status(404).body(createErrorResponse("No history found for test ID: " + testId));
        }
        return ResponseEntity.ok(history);
    }
}