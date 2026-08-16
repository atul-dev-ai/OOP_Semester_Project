package com.example.clubmanagement.dto;

import lombok.Data;

@Data
public class StudentRegistrationRequest {
    private String name;
    private String studentId;
    private String email;
    private String password;
    private String department;
}
