package com.alberto.multidigitalwallet.user_service.sedders;

import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import com.alberto.multidigitalwallet.user_service.repository.RoleRepository;
import com.alberto.multidigitalwallet.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADM_EMAIL}")
    private String adminEmail;

    @Value("${ADM_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_ADMIN").isPresent()){
            log.info("Admin User already exists");
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(()-> {

                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    return roleRepository.save(newRole);
                });

        User admin = new User();
        admin.setName("ADMIN");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.getRoles().add(adminRole);

        userRepository.save(admin);
        log.info("Admin user created: {}", adminEmail);

    }
}
