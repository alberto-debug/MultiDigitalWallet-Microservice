package com.alberto.walletService.DTOs;

import com.alberto.walletService.model.ENUMs.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotNull(message = "currency is required")
        Currency currency,

        @NotNull(message = "amount is required")
        @Positive(message = "amount must be greater than 0")
        BigDecimal amount
) {
}
