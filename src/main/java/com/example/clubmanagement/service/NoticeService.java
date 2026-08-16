package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Notice;
import com.example.clubmanagement.repository.ClubRepository;
import com.example.clubmanagement.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    
    private final NoticeRepository noticeRepository;
    private final ClubRepository clubRepository;

    public NoticeService(NoticeRepository noticeRepository, ClubRepository clubRepository) {
        this.noticeRepository = noticeRepository;
        this.clubRepository = clubRepository;
    }

    public Notice createNotice(Notice notice, Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("Club not found"));
        notice.setClub(club);
        return noticeRepository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }
    
    public List<Notice> getNoticesByClubId(Long clubId) {
        return noticeRepository.findByClubId(clubId);
    }
}
