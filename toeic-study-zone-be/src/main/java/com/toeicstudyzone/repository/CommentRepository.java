package com.toeicstudyzone.repository;

import com.toeicstudyzone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTestId(Long testId);

    List<Comment> findByParentId(Long parentId);

    long countByTestId(Long testId); 
}