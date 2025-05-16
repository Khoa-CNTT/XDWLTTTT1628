package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.CommentDTO;
import com.toeicstudyzone.entity.Comment;
import com.toeicstudyzone.repository.CommentRepository;
import com.toeicstudyzone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByTestId(Long testId) {
        List<Comment> comments = commentRepository.findByTestId(testId);
        return comments.stream().map(comment -> new CommentDTO(
            comment.getId(),
            comment.getUser().getId(),
            comment.getTest().getId(),
            comment.getTest().getTitle(),
            comment.getTest().getTestYear().getYear(),
            comment.getParent() != null ? comment.getParent().getId() : null,
            comment.getCommentText(),
            comment.getCreatedAt(),
            comment.getUpdatedAt(),
            comment.getStatus().name()
        )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getRepliesByParentId(Long parentId) {
        List<Comment> replies = commentRepository.findByParentId(parentId);
        return replies.stream().map(comment -> new CommentDTO(
            comment.getId(),
            comment.getUser().getId(),
            comment.getTest().getId(),
            comment.getTest().getTitle(),
            comment.getTest().getTestYear().getYear(),
            comment.getParent() != null ? comment.getParent().getId() : null,
            comment.getCommentText(),
            comment.getCreatedAt(),
            comment.getUpdatedAt(),
            comment.getStatus().name()
        )).collect(Collectors.toList());
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