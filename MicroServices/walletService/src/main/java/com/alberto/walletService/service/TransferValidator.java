package com.alberto.walletService.service;

import com.alberto.walletService.DTOs.TransferRequest;
import com.alberto.walletService.exception.InsufficientBalanceException;
import com.alberto.walletService.exception.WalletNotFoundException;
import com.alberto.walletService.model.Wallet;
import com.alberto.walletService.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferValidator {

    private final WalletRepository walletRepository;

    public void validateTransfer(TransferRequest transferRequest) {
        validateWalletsExist(transferRequest.fromWalletId(), transferRequest.toWalletId());
        validateWalletsAreDifferent(transferRequest.fromWalletId(), transferRequest.toWalletId());
        validateSufficientBalance(transferRequest.fromWalletId(), transferRequest.amount());
    }

    private void validateWalletsExist(UUID fromWalletId, UUID toWalletId) {
        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new WalletNotFoundException("Source wallet not found with id: " + fromWalletId));

        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new WalletNotFoundException("Destination wallet not found with id: " + toWalletId));
    }
    
    private void validateWalletsAreDifferent(UUID fromWalletId, UUID toWalletId) {
        if (fromWalletId.equals(toWalletId)) {
            throw new IllegalArgumentException("Source and destination wallets must be different");
        }
    }

    private void validateSufficientBalance(UUID walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + walletId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient funds. Current balance: " + wallet.getBalance() + ", Required: " + amount
            );
        }

    }

}
