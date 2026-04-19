package com.ebdms.backend.config;

import com.ebdms.backend.enums.Role;
import com.ebdms.backend.model.*;
import com.ebdms.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        // إنشاء Super Admin فقط (عشان يقدر يدخل يضيف البنوك)
        if (!userRepository.existsByEmail("admin@ebdms.com")) {
            User adminUser = User.builder()
                    .email("admin@ebdms.com")
                    .password("admin123")
                    .role(Role.ADMIN)
                    .isActive(true)
                    .build();

            Admin adminProfile = Admin.builder()
                    .user(adminUser)
                    .fullName("Super Admin")
                    .nationalId("100200300")
                    .build();

            adminRepository.save(adminProfile);
            System.out.println("✅ SUPER ADMIN CREATED: admin@ebdms.com / admin123");
        }
    }
}