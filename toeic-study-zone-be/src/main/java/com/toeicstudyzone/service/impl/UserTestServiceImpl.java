package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.dto.response.QuestionResultDTO;
import com.toeicstudyzone.dto.request.TestReviewRequest;
import com.toeicstudyzone.entity.UserQuestionResponse;
import com.toeicstudyzone.entity.UserTestHistory;
import com.toeicstudyzone.entity.AnswerOption;
import com.toeicstudyzone.repository.UserQuestionResponseRepository;
import com.toeicstudyzone.repository.UserTestHistoryRepository;
import com.toeicstudyzone.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTestServiceImpl implements UserTestService {
    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Autowired
    private UserQuestionResponseRepository userQuestionResponseRepository;

    @Override
    public List<TestResultResponse> getUserTestHistory(Long userId) {
        List<UserTestHistory> histories = userTestHistoryRepository.findByUserId(userId);
        return histories.stream().map(history -> {
            TestResultResponse response = new TestResultResponse();
            response.setTotalScore(history.getTotalScore());
            response.setListeningScore(history.getListeningScore());
            response.setReadingScore(history.getReadingScore());
            response.setCorrectCount(history.getCorrectAnswers());
            response.setPercentage((double) history.getCorrectAnswers() / history.getTest().getTotalQuestions() * 100);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public TestResultResponse reviewTest(TestReviewRequest request) {
        UserTestHistory history = userTestHistoryRepository.findById(request.getUserTestHistoryId())
                .orElseThrow(() -> new RuntimeException("History not found"));
        TestResultResponse response = new TestResultResponse();
        response.setTotalScore(history.getTotalScore());
        response.setListeningScore(history.getListeningScore());
        response.setReadingScore(history.getReadingScore());
        response.setCorrectCount(history.getCorrectAnswers());
        response.setResults(userQuestionResponseRepository.findByUserTestHistoryId(history.getId()).stream().map(r -> {
            QuestionResultDTO result = new QuestionResultDTO();
            result.setQuestionId(r.getQuestion().getId());
            result.setCorrectAnswerId(r.getQuestion().getAnswerOptions().stream()
                    .filter(AnswerOption::isCorrect).findFirst().map(AnswerOption::getId).orElse(null));
            result.setSelectedAnswerId(r.getSelectedAnswer().getId());
            result.setCorrect(r.getIsCorrect() != null ? r.getIsCorrect() : false); // Sửa setIsCorrect thành setCorrect và ép kiểu Boolean thành boolean
            return result;
        }).collect(Collectors.toList()));
        return response;
    }
}