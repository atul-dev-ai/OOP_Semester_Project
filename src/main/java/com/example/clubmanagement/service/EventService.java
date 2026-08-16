package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.Club;
import com.example.clubmanagement.entity.Event;
import com.example.clubmanagement.entity.EventRegistration;
import com.example.clubmanagement.entity.Student;
import com.example.clubmanagement.repository.ClubRepository;
import com.example.clubmanagement.repository.EventRegistrationRepository;
import com.example.clubmanagement.repository.EventRepository;
import com.example.clubmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;

    public EventService(EventRepository eventRepository, EventRegistrationRepository registrationRepository,
                        StudentRepository studentRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.clubRepository = clubRepository;
    }

    public Event createEvent(Event event, Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("Club not found"));
        event.setClub(club);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public void registerForEvent(Long studentId, Long eventId) {
        Event event = getEventById(eventId);
        
        // Rule 1: Registration deadline
        if (event.getRegistrationDeadline() != null && LocalDate.now().isAfter(event.getRegistrationDeadline())) {
            throw new RuntimeException("Registration closed for this event.");
        }

        // Rule 2: Maximum participants
        long currentRegistrations = registrationRepository.countByEventId(eventId);
        if (event.getMaximumParticipants() != null && currentRegistrations >= event.getMaximumParticipants()) {
            throw new RuntimeException("Maximum participant limit reached.");
        }

        // Rule 3: Already registered
        if (registrationRepository.findByStudentIdAndEventId(studentId, eventId).isPresent()) {
            throw new RuntimeException("You are already registered for this event.");
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        EventRegistration registration = new EventRegistration();
        registration.setStudent(student);
        registration.setEvent(event);
        registrationRepository.save(registration);
    }
    
    public List<EventRegistration> getMyEvents(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }
    
    public List<Event> getEventsByClubId(Long clubId) {
        return eventRepository.findByClubId(clubId);
    }
}
