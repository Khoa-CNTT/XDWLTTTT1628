package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.TestSubmissionRequest;
import com.toeicstudyzone.dto.request.ToeicTestDTO;
import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.entity.ToeicTest;

import java.util.List;

public interface ToeicTestService {
    ToeicTestDTO getTest(Long testId);
    List<ToeicTestDTO> getTestsByYear(Long yearId); // Thêm phương thức này
    List<ToeicTestDTO> getAllTests(); // Thêm phương thức này
    TestResultResponse submitTest(Long userId, TestSubmissionRequest request);
    ToeicTest createTest(ToeicTest toeicTest); // Thêm phương thức này
    ToeicTest updateTest(Long id, ToeicTest toeicTest); // Thêm phương thức này
    void deleteTest(Long id); // Thêm phương thức này
}