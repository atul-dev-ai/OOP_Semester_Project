package com.example.clubmanagement.controller;

import com.example.clubmanagement.dto.LoginRequest;
import com.example.clubmanagement.dto.StudentRegistrationRequest;
import com.example.clubmanagement.entity.User;
import com.example.clubmanagement.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginRequest request, HttpSession session, Model model) {
        try {
            User user = authService.login(request);
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userRole", user.getRole().name());
            
            if ("ADMIN".equals(user.getRole().name())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(StudentRegistrationRequest request, Model model) {
        try {
            authService.registerStudent(request);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
