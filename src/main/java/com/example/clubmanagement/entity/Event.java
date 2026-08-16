package com.example.clubmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate eventDate;
    
    private LocalTime eventTime;
    
    private String venue;

    private LocalDate registrationDeadline;
    
    private Integer maximumParticipants;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
}
