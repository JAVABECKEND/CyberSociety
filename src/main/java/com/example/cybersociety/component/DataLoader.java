package com.example.cybersociety.component;
import com.example.cybersociety.entity.Admin;
import com.example.cybersociety.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    String mode;
    final AdminRepository userRepository;

    @Override
    public void run(String... args) {
        if (mode.equals("always")) {
            Admin user=Admin.builder()
                    .name ("Admin")
                    .roles ("ROLE_ADMIN")
                    .email ("email@gmail.com")
                    .password (passwordEncoder.encode ("1234"))
                    .build();
            userRepository.save(user);
        }
    }
}
