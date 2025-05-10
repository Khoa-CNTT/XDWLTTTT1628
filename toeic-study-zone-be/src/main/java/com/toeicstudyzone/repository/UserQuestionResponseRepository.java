package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.UserQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserQuestionResponseRepository extends JpaRepository<UserQuestionResponse, Long> {
    List<UserQuestionResponse> findByUserTestHistoryId(Long historyId);
}