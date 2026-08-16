package com.example.clubmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_registrations")
@Getter
@Setter
public class EventRegistration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private LocalDateTime registrationDate = LocalDateTime.now();

    private String status = "REGISTERED";
}
