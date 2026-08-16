package com.example.clubmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
@Getter
@Setter
public class Notice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime publishDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
}
