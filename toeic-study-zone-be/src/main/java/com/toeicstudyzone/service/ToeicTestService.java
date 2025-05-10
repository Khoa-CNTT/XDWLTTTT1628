package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.TestSubmissionRequest;
import com.toeicstudyzone.dto.response.ToeicTestResponse;
import com.toeicstudyzone.dto.response.TestResultResponse;

public interface ToeicTestService {
    ToeicTestResponse getTest(Long testId);
    TestResultResponse submitTest(Long userId, TestSubmissionRequest request);
}