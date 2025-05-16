package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.response.GoalCompletionResponse;
import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.TestStatisticResponse;
import com.toeicstudyzone.dto.response.UserStatisticResponse;
import com.toeicstudyzone.entity.LearningGoal;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.ToeicTestRepository;
import com.toeicstudyzone.repository.CommentRepository;
import com.toeicstudyzone.repository.LearningGoalRepository;

import com.toeicstudyzone.repository.*;

import com.toeicstudyzone.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired private TestHistoryRepository testHistoryRepository;
    @Autowired private LearningGoalRepository learningGoalRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<UserStatisticResponse> getTopUsersByMonthYear(int month, int year) {
        return testHistoryRepository.findAll().stream()
            .filter(h -> {
                LocalDateTime submitDate = h.getSubmitDate();
                return submitDate.getMonthValue() == month && submitDate.getYear() == year;
            })
            .collect(Collectors.groupingBy(h -> h.getUser().getFullName(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new UserStatisticResponse(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }

    @Override
    public List<TestStatisticResponse> getTopTestsByAttempt() {
        return testHistoryRepository.findAll().stream()
            .collect(Collectors.groupingBy(h -> h.getToeicTest().getTitle(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new TestStatisticResponse(e.getKey(), e.getValue().intValue()))
            .collect(Collectors.toList());
    }

    @Override
    public GoalCompletionResponse getGoalCompletionRate() {
        List<LearningGoal> goals = learningGoalRepository.findAll();
        long totalGoals = goals.size();
        long completed = goals.stream()
            .filter(g -> Boolean.TRUE.equals(g.getIsCompleted()))
            .count();
        double rate = totalGoals == 0 ? 0 : (completed * 100.0) / totalGoals;
        return new GoalCompletionResponse(totalGoals, completed, rate);
    }

    @Override
    public StatisticsResponse getStatistics() {
        // TODO Auto-generated method stub
        long totalUsers = userRepository.count();
    long totalTests = toeicTestRepository.count();
    long totalComments = commentRepository.count();
    long totalGoals = learningGoalRepository.count();

    return new StatisticsResponse(totalUsers, totalTests, totalComments, totalGoals);
    }
}
