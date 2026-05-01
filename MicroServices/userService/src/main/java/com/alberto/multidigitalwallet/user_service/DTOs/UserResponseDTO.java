package com.alberto.multidigitalwallet.user_service.DTOs;

import com.alberto.multidigitalwallet.user_service.model.Roles.Role;
import com.alberto.multidigitalwallet.user_service.model.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserResponseDTO(

       Long id,
       String name,
       String email,
       Set<String> roles
){

    public static UserResponseDTO from(User user){

        return new UserResponseDTO(

                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())

        );
    }


}
