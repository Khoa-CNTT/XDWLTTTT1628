package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.UserResponse;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.entity.UserRoleId;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.repository.CommentRepository;
import com.toeicstudyzone.repository.ToeicTestRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
import com.toeicstudyzone.repository.UserTestHistoryRepository;
import com.toeicstudyzone.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService { 

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    String role = user.getUserRoles().isEmpty() ? null :
                            user.getUserRoles().iterator().next().getRole().getName().replace("ROLE_", "");
                    return new UserResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getFullName(),
                            user.getPhoneNumber(),
                            user.getBirthDate() != null ? user.getBirthDate() : null,
                            user.getAvatarUrl(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getStatus() != null ? (user.getStatus().name().equals("ACTIVE") ? "Hoạt động" : "Khóa") : null,
                            role
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void assignRole(Long userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setId(new UserRoleId(userId, roleId));
        userRoleRepository.save(userRole);
    }

    @Override
    public void lockUserAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(UserStatus.LOCKED);
        userRepository.save(user);
    }

   @Override
    public StatisticsResponse getUserStatistics() {
        StatisticsResponse response = new StatisticsResponse();
    response.setTotalUsers(userRepository.count());
    response.setTotalTests(toeicTestRepository.count());
    response.setCompletedTests(userTestHistoryRepository.countByCompleted(true));
    response.setAverageScore(userTestHistoryRepository.getAverageTotalScore());
    response.setTotalComments(commentRepository.count());
    return response;
    }

}
