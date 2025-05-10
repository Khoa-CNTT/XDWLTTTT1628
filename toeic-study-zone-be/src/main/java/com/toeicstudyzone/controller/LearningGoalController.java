package com.toeicstudyzone.controller;

import com.toeicstudyzone.service.LearningGoalService;
import com.toeicstudyzone.dto.request.LearningGoalRequest;
import com.toeicstudyzone.dto.response.LearningGoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/learning-goals")
public class LearningGoalController {

    @Autowired
    private LearningGoalService learningGoalService;

    @PostMapping
    public ResponseEntity<?> createLearningGoal(@RequestBody LearningGoalRequest request) {
        try {
            LearningGoalResponse response = learningGoalService.createLearningGoal(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error saving learning goal: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLearningGoalsByUser(@PathVariable Long userId) {
        List<LearningGoalResponse> goals = learningGoalService.getLearningGoalsByUserId(userId);
        if (goals.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "No learning goals found for user ID: " + userId);
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLearningGoal(@PathVariable Long id, @RequestBody LearningGoalRequest request) {
        try {
            Optional<LearningGoalResponse> response = learningGoalService.updateLearningGoal(id, request);
            if (!response.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Learning goal with ID " + id + " not found");
                return ResponseEntity.status(404).body(error);
            }
            return ResponseEntity.ok(response.get());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error updating learning goal: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLearningGoal(@PathVariable Long id) {
        try {
            learningGoalService.deleteLearningGoal(id);
            return ResponseEntity.ok("Learning goal deleted successfully");
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error deleting learning goal: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}