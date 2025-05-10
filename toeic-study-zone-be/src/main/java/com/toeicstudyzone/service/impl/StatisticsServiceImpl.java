package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserTestHistoryRepository;
import com.toeicstudyzone.repository.ToeicTestRepository;
import com.toeicstudyzone.repository.CommentRepository;
import com.toeicstudyzone.service.StatisticsService;
import com.toeicstudyzone.entity.UserTestHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public StatisticsResponse getStatistics() {
        long totalUsers = userRepository.count();
        long totalTests = toeicTestRepository.count();
        long completedTests = userTestHistoryRepository.countByCompletedTrue();
        double averageScore = userTestHistoryRepository.findByCompletedTrue().stream()
                .filter(history -> history.getTotalScore() != null)
                .collect(Collectors.averagingDouble(UserTestHistory::getTotalScore));
        long totalComments = commentRepository.count();

        return new StatisticsResponse(totalUsers, totalTests, completedTests, averageScore, totalComments);
    }
}