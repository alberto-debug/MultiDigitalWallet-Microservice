package com.alberto.multidigitalwallet.user_service.DTOs;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record LoginRequestDTO(

        @NotBlank(message = "email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "password is required")
        String password

) {}
