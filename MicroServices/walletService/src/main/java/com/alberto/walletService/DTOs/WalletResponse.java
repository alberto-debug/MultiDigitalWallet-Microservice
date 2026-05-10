package com.alberto.walletService.DTOs;

import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.ENUMs.WalletStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletResponse(


        UUID id,

        UUID userId,

        Currency currency,

        BigDecimal balance,

        WalletStatus status,

        LocalDateTime createdAt

) {
}
