package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    List<EventRegistration> findByStudentId(Long studentId);
    List<EventRegistration> findByEventId(Long eventId);
    Optional<EventRegistration> findByStudentIdAndEventId(Long studentId, Long eventId);
    long countByEventId(Long eventId);
}
