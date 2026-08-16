package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByClubId(Long clubId);
}
