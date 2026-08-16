package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.Notice;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticeController {
    
    private final NoticeService noticeService;
    private final ClubService clubService;

    public NoticeController(NoticeService noticeService, ClubService clubService) {
        this.noticeService = noticeService;
        this.clubService = clubService;
    }

    @GetMapping("/admin/notices")
    public String manageNotices(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("notices", noticeService.getAllNotices());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "admin_notices";
    }

    @PostMapping("/admin/notices")
    public String createNotice(Notice notice, @RequestParam Long clubId, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        noticeService.createNotice(notice, clubId);
        return "redirect:/admin/notices";
    }

    @GetMapping("/student/notices")
    public String viewNotices(HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        model.addAttribute("notices", noticeService.getAllNotices());
        return "student_notices";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userRole"));
    }

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("userRole"));
    }
}
