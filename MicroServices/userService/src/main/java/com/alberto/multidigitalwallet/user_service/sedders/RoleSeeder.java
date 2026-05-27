package com.alberto.multidigitalwallet.user_service.sedders;

import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_USER").isEmpty()){
            roleRepository.save(new Role(null, "ROLE_USER", new HashSet<>()));
        }

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()){
            roleRepository.save(new Role(null, "ROLE_ADMIN", new HashSet<>()));
        }

    }
}
