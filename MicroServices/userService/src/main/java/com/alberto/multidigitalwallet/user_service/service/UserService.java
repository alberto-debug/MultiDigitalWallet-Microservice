package com.alberto.multidigitalwallet.user_service.service;


import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import com.alberto.multidigitalwallet.user_service.repository.RoleRepository;
import com.alberto.multidigitalwallet.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public User authenticate(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("Invalid Password");
        }

        return user;
    }

    public User register(String name, String email, String password){

        if (userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("User already exists with this email");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(()-> new RuntimeException("Default Role user not found"));

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(Objects.requireNonNull(passwordEncoder.encode(password)));
        newUser.getRoles().add(role);

        return userRepository.save(newUser);
    }

    public User findByEmail(String email){

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("User with email " + email + "not found"));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User not found with id: "+ id));
    }

    public void deleteUser(Long id){

        User user = findById(id);
        userRepository.delete(user);
        log.debug("User deleted: {}", id);
    }



}
