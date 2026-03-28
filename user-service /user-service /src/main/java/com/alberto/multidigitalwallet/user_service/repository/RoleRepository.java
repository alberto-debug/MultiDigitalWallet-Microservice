package com.alberto.multidigitalwallet.user_service.repository;


import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
