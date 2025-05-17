    package com.toeicstudyzone.service;

    import com.toeicstudyzone.dto.request.TestSubmissionRequest;
    import com.toeicstudyzone.dto.request.ToeicTestDTO;
    import com.toeicstudyzone.dto.response.TestResultResponse;
    import com.toeicstudyzone.entity.ToeicTest;
    import com.toeicstudyzone.dto.response.LatestTestResponse;

    import java.util.List;

    public interface ToeicTestService {
        ToeicTestDTO getTest(Long testId);
        List<ToeicTestDTO> getTestsByYear(Long yearId);
        List<ToeicTestDTO> getAllTests();
        TestResultResponse submitTest(Long userId, TestSubmissionRequest request);
        ToeicTest createTest(ToeicTest toeicTest);
        ToeicTest updateTest(Long id, ToeicTest toeicTest);
        void deleteTest(Long id);

        List<LatestTestResponse> getLatestPublishedTests();
    }
