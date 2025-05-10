package com.toeicstudyzone.controller;

import com.toeicstudyzone.entity.ToeicTest;
import com.toeicstudyzone.repository.ToeicTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ToeicTestController {

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @GetMapping("/tests")
    public ResponseEntity<List<ToeicTest>> getAllTests() {
        List<ToeicTest> tests = toeicTestRepository.findAll();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/tests/year/{yearId}")
    public ResponseEntity<?> getTestsByYear(@PathVariable Long yearId) {
        List<ToeicTest> tests = toeicTestRepository.findByTestYearId(yearId);
        if (tests.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "No tests found for year ID: " + yearId);
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<?> getTestById(@PathVariable Long id) {
        Optional<ToeicTest> test = toeicTestRepository.findById(id);
        if (!test.isPresent()) {
            return ResponseEntity.status(404).body("Test not found with ID: " + id);
        }
        return ResponseEntity.ok(test.get());
    }

    @PostMapping("/tests")
    public ResponseEntity<?> createTest(@RequestBody ToeicTest toeicTest) {
        try {
            ToeicTest savedTest = toeicTestRepository.save(toeicTest);
            return ResponseEntity.ok(savedTest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving test: " + e.getMessage());
        }
    }

    @PutMapping("/tests/{id}")
    public ResponseEntity<?> updateTest(@PathVariable Long id, @RequestBody ToeicTest toeicTest) {
        Optional<ToeicTest> existingTest = toeicTestRepository.findById(id);
        if (!existingTest.isPresent()) {
            return ResponseEntity.status(404).body("Test not found with ID: " + id);
        }
        toeicTest.setId(id);
        ToeicTest updatedTest = toeicTestRepository.save(toeicTest);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/tests/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        Optional<ToeicTest> existingTest = toeicTestRepository.findById(id);
        if (!existingTest.isPresent()) {
            return ResponseEntity.status(404).body("Test not found with ID: " + id);
        }
        toeicTestRepository.deleteById(id);
        return ResponseEntity.ok("Test deleted successfully");
    }
}