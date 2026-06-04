package com.alberto.DTOs;

public record ApiResponseDTO<T>(
        boolean success,
        String message,
        T data
) {}
