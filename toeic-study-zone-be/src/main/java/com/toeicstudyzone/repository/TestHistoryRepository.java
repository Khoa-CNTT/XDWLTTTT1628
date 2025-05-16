package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.TestHistory;
import com.toeicstudyzone.entity.UserTestHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    List<UserTestHistory> findByUserId(Long userId);
}
