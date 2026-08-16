package com.example.clubmanagement.dto;

import lombok.Data;

@Data
public class ClubRequest {
    private String clubName;
    private String description;
    private String category;
    private String presidentName;
}
