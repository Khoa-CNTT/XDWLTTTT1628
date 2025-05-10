package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.TestSection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestSectionRepository extends JpaRepository<TestSection, Long> {
    List<TestSection> findByToeicTestId(Long testId);
}