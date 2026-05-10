package com.alberto.walletService.DTOs;

import com.alberto.walletService.model.ENUMs.Currency;
import jakarta.validation.constraints.NotNull;

public record CreateWalletRequest(

        @NotNull(message = "currency is required")
        Currency currency

) {

}
