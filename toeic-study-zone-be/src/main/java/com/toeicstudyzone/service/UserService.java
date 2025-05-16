package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.UserRequest;
import com.toeicstudyzone.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    List<UserResponse> getAllUsers(String keyword);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    UserResponse updateUserByEmail(String email, UserRequest userRequest);
    void deleteUser(Long id);
    String uploadAvatar(MultipartFile file);
}