package com.alberto.walletService.repository;

import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByUserIdAndCurrency(UUID userId, Currency currency);

    List<Wallet> findByUserId(UUID userId);

}
