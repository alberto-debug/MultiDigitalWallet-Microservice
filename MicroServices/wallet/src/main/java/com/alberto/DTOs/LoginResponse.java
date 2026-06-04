package com.alberto.DTOs;

public record LoginResponse(
        String token,
        UserDataDTO userData
) {}
