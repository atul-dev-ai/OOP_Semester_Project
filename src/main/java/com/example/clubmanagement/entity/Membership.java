package com.example.clubmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "memberships")
@Getter
@Setter
public class Membership {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    private LocalDateTime applicationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status = MembershipStatus.PENDING;
}
