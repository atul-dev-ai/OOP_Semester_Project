package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Membership;
import com.example.clubmanagement.entity.MembershipStatus;
import com.example.clubmanagement.entity.Student;
import com.example.clubmanagement.repository.ClubRepository;
import com.example.clubmanagement.repository.MembershipRepository;
import com.example.clubmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {
    
    private final MembershipRepository membershipRepository;
    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;

    public MembershipService(MembershipRepository membershipRepository, StudentRepository studentRepository, ClubRepository clubRepository) {
        this.membershipRepository = membershipRepository;
        this.studentRepository = studentRepository;
        this.clubRepository = clubRepository;
    }

    public void applyForMembership(Long studentId, Long clubId) {
        Optional<Membership> existing = membershipRepository.findByStudentIdAndClubId(studentId, clubId);
        if (existing.isPresent()) {
            throw new RuntimeException("Already applied or a member of this club");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        Membership membership = new Membership();
        membership.setStudent(student);
        membership.setClub(club);
        membership.setStatus(MembershipStatus.PENDING);
        membershipRepository.save(membership);
    }

    public void approveMembership(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
        membership.setStatus(MembershipStatus.APPROVED);
        membershipRepository.save(membership);
    }

    public void rejectMembership(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
        membership.setStatus(MembershipStatus.REJECTED);
        membershipRepository.save(membership);
    }

    public List<Membership> getPendingRequests() {
        return membershipRepository.findByStatus(MembershipStatus.PENDING);
    }
    
    public List<Membership> getMyMemberships(Long studentId) {
        return membershipRepository.findByStudentId(studentId);
    }
    
    public Optional<Membership> getMembershipStatus(Long studentId, Long clubId) {
        return membershipRepository.findByStudentIdAndClubId(studentId, clubId);
    }
}
