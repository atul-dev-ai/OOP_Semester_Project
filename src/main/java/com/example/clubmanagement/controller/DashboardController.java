package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"STUDENT".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "student_dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "admin_dashboard";
    }
}
