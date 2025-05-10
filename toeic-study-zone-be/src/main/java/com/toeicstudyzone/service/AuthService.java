package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.LoginRequest;
import com.toeicstudyzone.dto.request.RegisterRequest;
import com.toeicstudyzone.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse register(RegisterRequest registerRequest);
}