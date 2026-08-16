package com.example.clubmanagement.controller;

import com.example.clubmanagement.entity.Event;
import com.example.clubmanagement.entity.User;
import com.example.clubmanagement.service.ClubService;
import com.example.clubmanagement.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventController {
    
    private final EventService eventService;
    private final ClubService clubService;

    public EventController(EventService eventService, ClubService clubService) {
        this.eventService = eventService;
        this.clubService = clubService;
    }

    @GetMapping("/admin/events")
    public String manageEvents(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("clubs", clubService.getAllClubs());
        return "admin_events";
    }

    @PostMapping("/admin/events")
    public String createEvent(Event event, @RequestParam Long clubId, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        eventService.createEvent(event, clubId);
        return "redirect:/admin/events";
    }

    @GetMapping("/student/events")
    public String viewEvents(HttpSession session, Model model) {
        if (!isStudent(session)) return "redirect:/login";
        model.addAttribute("events", eventService.getAllEvents());
        return "student_events";
    }

    @PostMapping("/student/events/{id}/register")
    public String registerForEvent(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isStudent(session)) return "redirect:/login";
        User user = (User) session.getAttribute("loggedInUser");
        try {
            eventService.registerForEvent(user.getId(), id);
            redirectAttributes.addFlashAttribute("message", "Registered successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/student/events";
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userRole"));
    }

    private boolean isStudent(HttpSession session) {
        return "STUDENT".equals(session.getAttribute("userRole"));
    }
}
