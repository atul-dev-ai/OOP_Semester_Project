package com.example.clubmanagement.service;

import com.example.clubmanagement.dto.LoginRequest;
import com.example.clubmanagement.dto.StudentRegistrationRequest;
import com.example.clubmanagement.entity.Role;
import com.example.clubmanagement.entity.Student;
import com.example.clubmanagement.entity.User;
import com.example.clubmanagement.repository.StudentRepository;
import com.example.clubmanagement.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public AuthService(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public void registerStudent(StudentRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }
        if (studentRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new RuntimeException("Student ID already exists!");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        
        // Hash password with BCrypt
        student.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        
        student.setRole(Role.STUDENT);
        student.setStudentId(request.getStudentId());
        student.setDepartment(request.getDepartment());

        studentRepository.save(student);
    }

    public User login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
}
