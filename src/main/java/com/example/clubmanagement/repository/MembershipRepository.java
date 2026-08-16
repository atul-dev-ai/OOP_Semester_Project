package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Membership;
import com.example.clubmanagement.entity.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByStudentId(Long studentId);
    List<Membership> findByClubId(Long clubId);
    List<Membership> findByStatus(MembershipStatus status);
    Optional<Membership> findByStudentIdAndClubId(Long studentId, Long clubId);
}
