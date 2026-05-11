package com.alberto.walletService.service;


import com.alberto.walletService.DTOs.CreateWalletRequest;
import com.alberto.walletService.DTOs.WalletResponse;
import com.alberto.walletService.exception.WalletAlreadyExistsException;
import com.alberto.walletService.exception.WalletNotFoundException;
import com.alberto.walletService.mapper.WalletMapper;
import com.alberto.walletService.model.ENUMs.WalletStatus;
import com.alberto.walletService.model.Wallet;
import com.alberto.walletService.repository.TransactionRepository;
import com.alberto.walletService.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletResponse createWallet(UUID userId, CreateWalletRequest request){

        boolean alreadyExists = walletRepository.existsByUserIdAndCurrency(userId, request.currency());

        if (alreadyExists){
            throw new WalletAlreadyExistsException(
                    "User already has a " + request.currency() + " wallet"
            );
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCurrency(request.currency());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setWalletStatus(WalletStatus.ACTIVE);

        Wallet saved = walletRepository.save(wallet);
        
        return walletMapper.toResponse(saved);
    }

    public List<WalletResponse> getUserWallets(UUID userId){
        return walletRepository.findByUserId(userId)
                .stream()
                .map(walletMapper::toResponse)
                .toList();
    }

    public BigDecimal getBalance(UUID walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + walletId))
                .getBalance();
    }

}
