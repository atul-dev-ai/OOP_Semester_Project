package com.example.clubmanagement;

import com.example.clubmanagement.entity.Admin;
import com.example.clubmanagement.entity.Role;
import com.example.clubmanagement.repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentClubManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentClubManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(AdminRepository adminRepository) {
		return args -> {
			if (adminRepository.findByEmail("admin@scms.edu").isEmpty()) {
				Admin admin = new Admin();
				admin.setName("System Admin");
				admin.setEmail("admin@scms.edu");
				admin.setPassword(BCrypt.hashpw("admin123", BCrypt.gensalt()));
				admin.setRole(Role.ADMIN);
				adminRepository.save(admin);
				System.out.println("=========================================");
				System.out.println("Default Admin Created:");
				System.out.println("Email: admin@scms.edu");
				System.out.println("Password: admin123");
				System.out.println("=========================================");
			}
		};
	}
}
