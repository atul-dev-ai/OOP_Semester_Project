package com.example.clubmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends User {
    
    @Column(nullable = false, unique = true)
    private String studentId;

    @Column(nullable = false)
    private String department;

    @Override
    public String displayDashboard() {
        return "student_dashboard"; // This demonstrates Polymorphism
    }
}
