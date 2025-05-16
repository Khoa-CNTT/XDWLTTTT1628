package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.request.ToeicTestDTO;
import com.toeicstudyzone.entity.TestYear;
import com.toeicstudyzone.entity.ToeicTest;
import com.toeicstudyzone.repository.TestYearRepository;
import com.toeicstudyzone.service.ToeicTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tests")
public class ToeicTestController {

    @Autowired
    private ToeicTestService toeicTestService;

    @Autowired
    private TestYearRepository testYearRepository;

    @GetMapping
    public ResponseEntity<List<ToeicTestDTO>> getAllTests() {
        return ResponseEntity.ok(toeicTestService.getAllTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToeicTestDTO> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(toeicTestService.getTest(id));
    }

    @PostMapping
    public ResponseEntity<ToeicTestDTO> createTest(@RequestBody ToeicTestDTO dto) {
        ToeicTest test = convertToEntity(dto);
        ToeicTest created = toeicTestService.createTest(test);
        return ResponseEntity.ok(convertToDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToeicTestDTO> updateTest(@PathVariable Long id, @RequestBody ToeicTestDTO dto) {
        ToeicTest test = convertToEntity(dto);
        ToeicTest updated = toeicTestService.updateTest(id, test);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        toeicTestService.deleteTest(id);
        return ResponseEntity.ok("Test deleted successfully");
    }

    private ToeicTest convertToEntity(ToeicTestDTO dto) {
        if (dto == null || dto.getYearId() == null || dto.getYearId() < 2010) {
            throw new IllegalArgumentException("Invalid year (must be >= 2010)");
        }

        Optional<TestYear> testYearOpt = testYearRepository.findByYear(dto.getYearId());
        TestYear testYear = testYearOpt.orElseGet(() -> {
            TestYear newYear = new TestYear();
            newYear.setYear(dto.getYearId());
            return testYearRepository.save(newYear);
        });

        ToeicTest test = new ToeicTest();
        test.setId(dto.getId());
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setTotalQuestions(dto.getTotalQuestions());
        test.setTimeLimit(dto.getTimeLimit());
        test.setIsFree(dto.getIsFree() != null && dto.getIsFree());
        test.setIsPublished(dto.getIsPublished() != null && dto.getIsPublished());
        test.setIsPlacementTest(dto.getIsPlacementTest() != null && dto.getIsPlacementTest());
        test.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        test.setUpdatedAt(LocalDateTime.now());
        test.setTestYear(testYear);
        return test;
    }

    private ToeicTestDTO convertToDTO(ToeicTest test) {
        return new ToeicTestDTO(
                test.getId(),
                test.getTitle(),
                test.getTestYear().getYear(),
                test.getDescription(),
                test.getTotalQuestions(),
                test.getTimeLimit(),
                test.getIsFree(),
                test.getIsPublished(),
                test.getIsPlacementTest(),
                test.getCreatedAt(),
                test.getUpdatedAt()
        );
    }
}
