package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.dto.request.TestReviewRequest;
import java.util.List;

public interface UserTestService {
    List<TestResultResponse> getUserTestHistory(Long userId);
    TestResultResponse reviewTest(TestReviewRequest request);
}