package com.alberto.walletService.DTOs;

import com.alberto.walletService.model.ENUMs.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(

        @NotNull(message = "fromWalletId is required")
        UUID fromWalletId,

        @NotNull(message = "toWalletId is required")
        UUID toWalletId,

        @NotNull(message = "amount is required")
        @Positive(message = "amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "referenceId is required")
        UUID referenceId

) {
}
