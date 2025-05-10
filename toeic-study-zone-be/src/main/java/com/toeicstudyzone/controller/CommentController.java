package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.request.CommentRequest;
import com.toeicstudyzone.entity.Comment;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.ToeicTest;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.ToeicTestRepository;
import com.toeicstudyzone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequest request) {
        try {
            Comment comment = new Comment();

            // Xử lý user
            if (request.getUserId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "User ID is required");
                return ResponseEntity.status(400).body(error);
            }
            Optional<User> userOptional = userRepository.findById(request.getUserId());
            if (!userOptional.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "User with ID " + request.getUserId() + " not found");
                return ResponseEntity.status(404).body(error);
            }
            comment.setUser(userOptional.get());

            // Xử lý test
            if (request.getTestId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Test ID is required");
                return ResponseEntity.status(400).body(error);
            }
            Optional<ToeicTest> testOptional = toeicTestRepository.findById(request.getTestId());
            if (!testOptional.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Test with ID " + request.getTestId() + " not found");
                return ResponseEntity.status(404).body(error);
            }
            comment.setTest(testOptional.get());

            // Xử lý parent (nếu có)
            if (request.getParentId() != null) {
                Optional<Comment> parentOptional = commentService.findById(request.getParentId());
                if (!parentOptional.isPresent()) {
                    Map<String, String> error = new HashMap<>();
                    error.put("message", "Parent comment with ID " + request.getParentId() + " not found");
                    return ResponseEntity.status(404).body(error);
                }
                comment.setParent(parentOptional.get());
            }

            // Xử lý commentText
            if (request.getCommentText() == null || request.getCommentText().trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Comment text is required");
                return ResponseEntity.status(400).body(error);
            }
            comment.setCommentText(request.getCommentText());

            Comment savedComment = commentService.createComment(comment);
            return ResponseEntity.ok(savedComment);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error saving comment: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<?> getCommentsByTest(@PathVariable Long testId) {
        List<Comment> comments = commentService.getCommentsByTestId(testId);
        if (comments.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "No comments found for test ID: " + testId);
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/replies/{parentId}")
    public ResponseEntity<?> getRepliesByParent(@PathVariable Long parentId) {
        List<Comment> replies = commentService.getRepliesByParentId(parentId);
        if (replies.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "No replies found for parent comment ID: " + parentId);
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(replies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            if (!requestBody.containsKey("commentText") || requestBody.get("commentText").toString().trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Comment text is required");
                return ResponseEntity.status(400).body(error);
            }
            String commentText = requestBody.get("commentText").toString();
            Optional<Comment> updatedComment = commentService.updateComment(id, commentText);
            if (!updatedComment.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Comment with ID " + id + " not found");
                return ResponseEntity.status(404).body(error);
            }
            return ResponseEntity.ok(updatedComment.get());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error updating comment: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error deleting comment: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}