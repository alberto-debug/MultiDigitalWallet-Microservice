package com.alberto.walletService.DTOs;

import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.ENUMs.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(

        UUID id,

        UUID walletId,

        TransactionType type,

        BigDecimal amount,

        BigDecimal balanceBefore,

        BigDecimal balanceAfter,

        Currency currency,

        String description,

        LocalDateTime createdAt

) {
}
