package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.entity.Comment;
import com.toeicstudyzone.repository.CommentRepository;
import com.toeicstudyzone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        if (comment.getUser() == null || comment.getTest() == null || comment.getCommentText() == null || comment.getCommentText().trim().isEmpty()) {
            throw new IllegalArgumentException("User, test, and comment text are required");
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTestId(Long testId) {
        return commentRepository.findByTestId(testId);
    }

    @Override
    public List<Comment> getRepliesByParentId(Long parentId) {
        return commentRepository.findByParentId(parentId);
    }

    @Override
    public Optional<Comment> updateComment(Long id, String commentText) {
        if (commentText == null || commentText.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text is required");
        }
        return commentRepository.findById(id).map(comment -> {
            comment.setCommentText(commentText);
            return commentRepository.save(comment);
        });
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}