package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByClubId(Long clubId);
}
