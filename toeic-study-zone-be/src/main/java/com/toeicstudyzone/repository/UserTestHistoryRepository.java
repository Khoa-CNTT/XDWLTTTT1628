package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.UserTestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTestHistoryRepository extends JpaRepository<UserTestHistory, Long> {
    List<UserTestHistory> findByUserId(Long userId);

    List<UserTestHistory> findByTestId(Long testId);

    List<UserTestHistory> findByCompletedTrue();

    long countByCompletedTrue();
}