package com.alberto.walletService.model;


import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.ENUMs.WalletStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
public class wallet {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private WalletStatus walletStatus;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
