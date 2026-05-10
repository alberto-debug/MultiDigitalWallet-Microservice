package com.alberto.walletService.repository;

import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.Wallet;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByUserIdAndCurrency(UUID userId, @NotNull(message = "currency is required") Currency currency);
}
