package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.entity.LearningGoal;
import com.toeicstudyzone.repository.LearningGoalRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.service.LearningGoalService;
import com.toeicstudyzone.dto.request.LearningGoalRequest;
import com.toeicstudyzone.dto.response.LearningGoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearningGoalServiceImpl implements LearningGoalService {

    @Autowired
    private LearningGoalRepository learningGoalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LearningGoalResponse createLearningGoal(LearningGoalRequest request) {
        if (request.getUserId() == null || request.getTargetScore() == null || request.getTargetDate() == null) {
            throw new IllegalArgumentException("User ID, target score, and target date are required");
        }
        LearningGoal learningGoal = new LearningGoal();
        learningGoal.setTargetScore(request.getTargetScore());
        learningGoal.setTargetDate(request.getTargetDate());
        learningGoal.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        return toResponse(learningGoalRepository.save(learningGoal));
    }

    @Override
    public List<LearningGoalResponse> getLearningGoalsByUserId(Long userId) {
        return learningGoalRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LearningGoalResponse> updateLearningGoal(Long id, LearningGoalRequest request) {
        return learningGoalRepository.findById(id).map(learningGoal -> {
            if (request.getTargetScore() != null) {
                learningGoal.setTargetScore(request.getTargetScore());
            }
            if (request.getTargetDate() != null) {
                learningGoal.setTargetDate(request.getTargetDate());
            }
            return toResponse(learningGoalRepository.save(learningGoal));
        });
    }

    @Override
    public void deleteLearningGoal(Long id) {
        learningGoalRepository.deleteById(id);
    }

    @Override
    public Optional<LearningGoal> findById(Long id) {
        return learningGoalRepository.findById(id);
    }

    private LearningGoalResponse toResponse(LearningGoal learningGoal) {
        LearningGoalResponse response = new LearningGoalResponse();
        response.setId(learningGoal.getId());
        response.setUserId(learningGoal.getUser().getId());
        response.setTargetScore(learningGoal.getTargetScore());
        response.setTargetDate(learningGoal.getTargetDate());
        response.setCreatedAt(learningGoal.getCreatedAt());
        response.setUpdatedAt(learningGoal.getUpdatedAt());
        return response;
    }
}