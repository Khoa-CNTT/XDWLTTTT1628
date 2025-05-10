package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.response.StatisticsResponse;
import com.toeicstudyzone.dto.response.UserResponse;

import java.util.List;

public interface AdminService {

    // Quản lý user
    List<UserResponse> getAllUsers();
    void assignRole(Long userId, Integer roleId);
    void lockUserAccount(Long userId);

    // Thống kê người dùng
    StatisticsResponse getUserStatistics();
}