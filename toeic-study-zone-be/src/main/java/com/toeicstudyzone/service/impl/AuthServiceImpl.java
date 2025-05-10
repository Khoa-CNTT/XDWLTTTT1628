package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.LoginRequest;
import com.toeicstudyzone.dto.request.RegisterRequest;
import com.toeicstudyzone.dto.response.JwtResponse;
import com.toeicstudyzone.entity.Role;
import com.toeicstudyzone.entity.User;
import com.toeicstudyzone.enums.UserStatus;
import com.toeicstudyzone.exception.BadRequestException;
import com.toeicstudyzone.repository.RoleRepository;
import com.toeicstudyzone.repository.UserRepository;
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
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new BadRequestException("Username and password are required");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return new JwtResponse(token);
    }

    @Override
    public JwtResponse register(RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null ||
            registerRequest.getEmail() == null) {
            throw new BadRequestException("Username, password, and email are required");
        }

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new BadRequestException("Username " + registerRequest.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email " + registerRequest.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        // Gán role mặc định (ví dụ: ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BadRequestException("Default role not found"));
        user.addRole(userRole);

        userRepository.save(user);

        // Xác thực lại để tạo token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return new JwtResponse(token);
    }
}