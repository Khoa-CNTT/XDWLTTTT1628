package com.toeicstudyzone.service;

import com.toeicstudyzone.entity.LearningGoal;
import com.toeicstudyzone.dto.request.LearningGoalRequest;
import com.toeicstudyzone.dto.response.LearningGoalResponse;
import java.util.List;
import java.util.Optional;

public interface LearningGoalService {
    LearningGoalResponse createLearningGoal(LearningGoalRequest request);
    List<LearningGoalResponse> getLearningGoalsByUserId(Long userId);
    Optional<LearningGoalResponse> updateLearningGoal(Long id, LearningGoalRequest request);
    void deleteLearningGoal(Long id);
    Optional<LearningGoal> findById(Long id);
}