package com.example.clubmanagement.service;

import com.example.clubmanagement.dto.ClubRequest;
import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Club createClub(ClubRequest request) {
        Club club = new Club();
        club.setClubName(request.getClubName());
        club.setDescription(request.getDescription());
        club.setCategory(request.getCategory());
        club.setPresidentName(request.getPresidentName());
        club.setStatus("ACTIVE");
        return clubRepository.save(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
    }
    
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}
