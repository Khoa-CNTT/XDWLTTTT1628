package com.toeicstudyzone.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

}