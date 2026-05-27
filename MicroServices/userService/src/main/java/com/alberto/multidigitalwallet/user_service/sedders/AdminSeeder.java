package com.alberto.multidigitalwallet.user_service.sedders;

import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import com.alberto.multidigitalwallet.user_service.repository.RoleRepository;
import com.alberto.multidigitalwallet.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    public void run(String @NonNull ... args) {


        if (userRepository.findByEmail(adminEmail).isPresent()) {
            log.info("Admin user already exists: {}", adminEmail);
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_ADMIN");
                    return roleRepository.save(newRole);
                });

        User admin = new User();
        admin.setName("ADMIN");
        admin.setEmail(adminEmail);
        admin.setPassword(Objects.requireNonNull(passwordEncoder.encode(adminPassword)));
        admin.getRoles().add(adminRole);

        userRepository.save(admin);
        log.info("Admin user created: {}", adminEmail);
    }
}
