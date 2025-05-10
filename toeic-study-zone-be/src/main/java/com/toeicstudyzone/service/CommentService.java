package com.toeicstudyzone.service;

import com.toeicstudyzone.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByTestId(Long testId);
    List<Comment> getRepliesByParentId(Long parentId);
    Optional<Comment> updateComment(Long id, String commentText);
    void deleteComment(Long id);
    Optional<Comment> findById(Long id);
}