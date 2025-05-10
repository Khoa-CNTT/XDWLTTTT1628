package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.TestYear;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TestYearRepository extends JpaRepository<TestYear, Long> {
    Optional<TestYear> findByYear(Integer year);
}