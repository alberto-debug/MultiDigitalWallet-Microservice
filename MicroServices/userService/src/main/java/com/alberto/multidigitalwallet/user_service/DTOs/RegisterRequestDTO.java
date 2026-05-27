package com.alberto.multidigitalwallet.user_service.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(


        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name should be within 3 to 50 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Message should be at least 6 characters")
        String password

) {
}
