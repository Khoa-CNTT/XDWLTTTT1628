package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingParagraphRepository extends JpaRepository<Paragraph, Long> {
}