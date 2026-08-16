package com.example.clubmanagement.controller;

import com.example.clubmanagement.dto.ClubRequest;
import com.example.clubmanagement.service.ClubService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/admin/clubs")
    public String manageClubs(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("clubs", clubService.getAllClubs());
        return "admin_clubs";
    }

    @PostMapping("/admin/clubs")
    public String createClub(ClubRequest request, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        clubService.createClub(request);
        return "redirect:/admin/clubs";
    }

    @GetMapping("/student/clubs")
    public String viewClubs(HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        model.addAttribute("clubs", clubService.getAllClubs());
        return "student_clubs";
    }

    @GetMapping("/student/clubs/{id}")
    public String viewClubDetails(@PathVariable Long id, HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        model.addAttribute("club", clubService.getClubById(id));
        return "club_details";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userRole"));
    }

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("userRole"));
    }
}
