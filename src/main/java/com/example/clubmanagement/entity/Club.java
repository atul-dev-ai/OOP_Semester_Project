package com.example.clubmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clubs")
@Getter
@Setter
public class Club {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clubName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;
    
    private String presidentName;

    private String status = "ACTIVE";
}
