package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.UserResponse;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.entity.UserRoleId;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
import com.toeicstudyzone.service.AdminService;
import com.toeicstudyzone.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getPhoneNumber(),
                        user.getBirthDate() != null ? user.getBirthDate() : null,
                        user.getAvatarUrl(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.getStatus() != null ? user.getStatus().toString() : null))
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
        return statisticsService.getStatistics();
    }
}