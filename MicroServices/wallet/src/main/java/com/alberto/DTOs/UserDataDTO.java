package com.alberto.DTOs;

import java.util.Set;

public record UserDataDTO(
        Long id,
        String name,
        String email,
        Set<String> roles
) {}
