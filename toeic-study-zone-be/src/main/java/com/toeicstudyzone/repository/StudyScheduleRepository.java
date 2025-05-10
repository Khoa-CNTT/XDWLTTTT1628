package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    List<StudySchedule> findByUserIdAndEndTimeAfter(Long userId, LocalDateTime now);
    List<StudySchedule> findByIsCompletedFalse();
}