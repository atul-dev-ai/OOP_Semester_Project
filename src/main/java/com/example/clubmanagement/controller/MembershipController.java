package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.User;
import com.example.clubmanagement.service.MembershipService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/student/clubs/{id}/apply")
    public String applyMembership(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isStudent(session)) return "redirect:/login";
        User user = (User) session.getAttribute("loggedInUser");
        try {
            membershipService.applyForMembership(user.getId(), id);
            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/clubs/" + id;
    }

    @GetMapping("/admin/memberships")
    public String viewPendingRequests(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("requests", membershipService.getPendingRequests());
        model.addAttribute("approvedMemberships", membershipService.getApprovedMemberships());
        return "admin_memberships";
    }

    @PostMapping("/admin/memberships/{id}/approve")
    public String approveMembership(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        membershipService.approveMembership(id);
        return "redirect:/admin/memberships";
    }

    @PostMapping("/admin/memberships/{id}/reject")
    public String rejectMembership(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        membershipService.rejectMembership(id);
        return "redirect:/admin/memberships";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userRole"));
    }

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("userRole"));
    }
}
