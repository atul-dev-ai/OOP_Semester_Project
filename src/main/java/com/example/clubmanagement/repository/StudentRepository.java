package com.example.clubmanagement.repository;

import com.example.clubmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentId(String studentId);
}
