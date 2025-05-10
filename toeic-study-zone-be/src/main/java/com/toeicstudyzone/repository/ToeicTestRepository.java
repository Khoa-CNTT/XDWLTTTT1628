package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.ToeicTest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToeicTestRepository extends JpaRepository<ToeicTest, Long> {
    List<ToeicTest> findByTestYearId(Long yearId);
    
}