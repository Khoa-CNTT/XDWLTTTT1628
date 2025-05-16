package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.UserRequest;
import com.toeicstudyzone.dto.response.UserResponse;
import com.toeicstudyzone.entity.Role;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.entity.UserRoleId;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.exception.BadRequestException;
import com.toeicstudyzone.exception.ResourceNotFoundException;
import com.toeicstudyzone.repository.RoleRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
import com.toeicstudyzone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
        return convertToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return convertToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllUsers(String keyword) {
        List<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            users = userRepository.findAll();
        }
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getEmail() == null || userRequest.getPassword() == null) {
            throw new BadRequestException("Username, email, and password are required");
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email " + userRequest.getEmail() + " already exists");
        }
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new BadRequestException("Username " + userRequest.getUsername() + " already exists");
        }

        if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
            if (!userRequest.getPhoneNumber().matches("\\d{10}")) {
                throw new BadRequestException("Phone number must be exactly 10 digits");
            }
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setBirthDate(userRequest.getBirthDate());
        user.setAvatarUrl(userRequest.getAvatarUrl());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setStatus(userRequest.getStatus() != null ? 
            UserStatus.valueOf(userRequest.getStatus().equals("Hoạt động") ? "ACTIVE" : "LOCKED") : 
            UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        if (userRequest.getRole() != null && !userRequest.getRole().isEmpty()) {
            String roleName = "ROLE_" + userRequest.getRole().toUpperCase().replace(" ", "_");
            System.out.println("Creating user with role: " + roleName);
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new BadRequestException("Role " + roleName + " not found"));
            UserRole userRole = new UserRole();
            userRole.setId(new UserRoleId(user.getId(), role.getId()));
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        return convertToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        try {
            System.out.println("Updating user with ID: " + id + ", Request: " + userRequest);
            if (userRequest.getEmail() == null) {
                throw new BadRequestException("Email is required");
            }

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

            userRepository.findByEmail(userRequest.getEmail())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(id)) {
                            throw new BadRequestException("Email " + userRequest.getEmail() + " already exists");
                        }
                    });

            if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
                if (!userRequest.getPhoneNumber().matches("\\d{10}")) {
                    throw new BadRequestException("Phone number must be exactly 10 digits");
                }
            }

            user.setEmail(userRequest.getEmail());
            user.setFullName(userRequest.getFullName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setBirthDate(userRequest.getBirthDate());
            if (userRequest.getAvatarUrl() != null) {
                user.setAvatarUrl(userRequest.getAvatarUrl());
            }

            if (userRequest.getStatus() != null) {
                if (!userRequest.getStatus().equals("Hoạt động") && !userRequest.getStatus().equals("Khóa")) {
                    throw new BadRequestException("Invalid status: " + userRequest.getStatus() + ". Must be 'Hoạt động' or 'Khóa'");
                }
                user.setStatus(UserStatus.valueOf(userRequest.getStatus().equals("Hoạt động") ? "ACTIVE" : "LOCKED"));
            }

            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }

            if (userRequest.getRole() != null && !userRequest.getRole().isEmpty()) {
                String roleName = "ROLE_" + userRequest.getRole().toUpperCase().replace(" ", "_");
                System.out.println("Updating user with role: " + roleName);
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new BadRequestException("Role " + roleName + " not found"));
                userRoleRepository.deleteByUserId(id);
                UserRole userRole = new UserRole();
                userRole.setId(new UserRoleId(user.getId(), role.getId()));
                userRole.setUser(user);
                userRole.setRole(role);
                userRoleRepository.save(userRole);
            }

            user.setUpdatedAt(LocalDateTime.now());
            User updatedUser = userRepository.save(user);
            return convertToUserResponse(updatedUser);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateUser: " + e.getMessage());
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public UserResponse updateUserByEmail(String email, UserRequest userRequest) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

            if (!userRequest.getEmail().equals(email)) {
                userRepository.findByEmail(userRequest.getEmail())
                        .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(user.getId())) {
                                throw new BadRequestException("Email " + userRequest.getEmail() + " already exists");
                            }
                        });
                user.setEmail(userRequest.getEmail());
            }

            if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
                if (!userRequest.getPhoneNumber().matches("\\d{10}")) {
                    throw new BadRequestException("Phone number must be exactly 10 digits");
                }
            }

            if (userRequest.getFullName() != null) {
                user.setFullName(userRequest.getFullName());
            }
            user.setPhoneNumber(userRequest.getPhoneNumber());
            if (userRequest.getBirthDate() != null) {
                user.setBirthDate(userRequest.getBirthDate());
            }
            if (userRequest.getAvatarUrl() != null) {
                user.setAvatarUrl(userRequest.getAvatarUrl());
                System.out.println("Updated avatarUrl in database: " + userRequest.getAvatarUrl());
            } else {
                System.out.println("No new avatarUrl provided, keeping existing: " + user.getAvatarUrl());
            }

            user.setUpdatedAt(LocalDateTime.now());
            User updatedUser = userRepository.save(user);
            return convertToUserResponse(updatedUser);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            System.out.println("Deleting user with ID: " + id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
            userRoleRepository.deleteByUserId(id);
            userRepository.delete(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleteUser: " + e.getMessage());
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        try {
            String uploadDir = "uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("Created directory: " + uploadDir);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            System.out.println("File saved to: " + filePath.toAbsolutePath()); // Debug

            // Tạo URL chính xác dựa trên context path của server
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/avatars/")
                    .path(fileName)
                    .toUriString();
            System.out.println("Generated avatar URL: " + baseUrl); // Debug
            return baseUrl;
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage()); // Debug
            throw new RuntimeException("Failed to store avatar: " + e.getMessage());
        }
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setBirthDate(user.getBirthDate());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setStatus(user.getStatus() != null ? 
            (user.getStatus().name().equals("ACTIVE") ? "Hoạt động" : "Khóa") : null);

        List<UserRole> userRoles = userRoleRepository.findById_UserIdWithRole(user.getId());
        if (!userRoles.isEmpty()) {
            response.setRole(userRoles.get(0).getRole().getName().replace("ROLE_", ""));
        }

        return response;
    }
}