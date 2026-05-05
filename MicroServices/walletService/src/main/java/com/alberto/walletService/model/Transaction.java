package com.alberto.walletService.model;

import com.alberto.walletService.model.ENUMs.Currency;
import com.alberto.walletService.model.ENUMs.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID walledId;
    private UUID referenceId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String description;
    private LocalDateTime createdAt;


}
