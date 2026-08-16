package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
