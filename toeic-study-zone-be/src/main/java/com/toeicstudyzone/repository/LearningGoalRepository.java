package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.LearningGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningGoalRepository extends JpaRepository<LearningGoal, Long> {
    List<LearningGoal> findByUserId(Long userId);
}