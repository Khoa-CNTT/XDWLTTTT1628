package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageRepository extends JpaRepository<Passage, Long> {
}