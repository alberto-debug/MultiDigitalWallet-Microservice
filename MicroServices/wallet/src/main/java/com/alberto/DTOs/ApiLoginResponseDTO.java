package com.alberto.DTOs;

import java.util.Set;

public record ApiLoginResponseDTO(
        boolean success,
        String message,
        UserDataDTO data
) {}
