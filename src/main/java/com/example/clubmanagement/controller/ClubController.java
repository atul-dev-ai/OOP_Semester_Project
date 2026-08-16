package com.example.clubmanagement.controller;

import com.example.clubmanagement.dto.ClubRequest;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.MembershipService;
import com.example.clubmanagement.service.EventService;
import com.example.clubmanagement.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClubController {

    private final ClubService clubService;
    private final MembershipService membershipService;
    private final EventService eventService;
    private final NoticeService noticeService;

    public ClubController(ClubService clubService, MembershipService membershipService,
                          EventService eventService, NoticeService noticeService) {
        this.clubService = clubService;
        this.membershipService = membershipService;
        this.eventService = eventService;
        this.noticeService = noticeService;
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
        
        com.example.clubmanagement.entity.User user = (com.example.clubmanagement.entity.User) session.getAttribute("loggedInUser");
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }
        Long studentId = user.getId();
        
        model.addAttribute("club", clubService.getClubById(id));
        
        java.util.Optional<com.example.clubmanagement.entity.Membership> membership = membershipService.getMembershipStatus(studentId, id);
        
        if (membership.isPresent()) {
            model.addAttribute("membershipStatus", membership.get().getStatus().name());
            if (membership.get().getStatus() == com.example.clubmanagement.entity.MembershipStatus.APPROVED) {
                model.addAttribute("clubEvents", eventService.getEventsByClubId(id));
                model.addAttribute("clubNotices", noticeService.getNoticesByClubId(id));
            }
        } else {
            model.addAttribute("membershipStatus", "NONE");
        }
        
        return "club_details";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userRole"));
    }

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("userRole"));
    }
}
