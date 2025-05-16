package com.toeicstudyzone.dto.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String message;
    private boolean success;

    public JwtResponse(String token, String message, boolean success) {
        this.token = token;
        this.message = message;
        this.success = success;
    }
}