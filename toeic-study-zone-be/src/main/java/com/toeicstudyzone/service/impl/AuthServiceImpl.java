package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.LoginRequest;
import com.toeicstudyzone.dto.request.RegisterRequest;
import com.toeicstudyzone.dto.response.JwtResponse;
import com.toeicstudyzone.entity.Role;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.entity.UserRole;
import com.toeicstudyzone.entity.UserRoleId;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.exception.BadRequestException;
import com.toeicstudyzone.repository.RoleRepository;
import com.toeicstudyzone.repository.UserRepository;
import com.toeicstudyzone.repository.UserRoleRepository;
import com.toeicstudyzone.security.JwtTokenProvider;
import com.toeicstudyzone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new BadRequestException("Email and password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = jwtTokenProvider.generateToken(authentication);
            return new JwtResponse(token, "Đăng nhập thành công", true);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            throw new BadRequestException("Đăng nhập thất bại: " + e.getMessage());
        }
    }

    @Override
    public JwtResponse register(RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getEmail() == null || registerRequest.getPassword() == null || registerRequest.getBirthDate() == null) {
            throw new BadRequestException("Username, email, password, and birth date are required");
        }

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new BadRequestException("Username " + registerRequest.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email " + registerRequest.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFullName(registerRequest.getFullName() != null ? registerRequest.getFullName() : "");
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setBirthDate(registerRequest.getBirthDate());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        // Gán role mặc định (ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BadRequestException("Default role not found"));
        UserRole userRoleEntity = new UserRole();
        userRoleEntity.setId(new UserRoleId(user.getId(), userRole.getId()));
        userRoleEntity.setUser(user);
        userRoleEntity.setRole(userRole);
        userRoleRepository.save(userRoleEntity);

        // Tạo token sau khi đăng ký
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(token, "Đăng ký thành công!", true);
    }
}