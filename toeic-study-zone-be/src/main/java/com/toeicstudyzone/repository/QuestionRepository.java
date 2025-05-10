package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}