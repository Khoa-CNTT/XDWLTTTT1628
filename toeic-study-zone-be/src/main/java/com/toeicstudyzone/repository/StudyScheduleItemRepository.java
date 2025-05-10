package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.StudyScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyScheduleItemRepository extends JpaRepository<StudyScheduleItem, Long> {
    List<StudyScheduleItem> findByScheduleId(Long scheduleId);
}