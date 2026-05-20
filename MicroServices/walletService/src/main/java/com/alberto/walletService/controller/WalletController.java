package com.alberto.walletService.controller;

import com.alberto.walletService.DTOs.ApiResponse;
import com.alberto.walletService.DTOs.CreateWalletRequest;
import com.alberto.walletService.DTOs.DepositRequest;
import com.alberto.walletService.DTOs.WalletResponse;
import com.alberto.walletService.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletService walletService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<WalletResponse>> createWallet(
            @Valid @RequestBody CreateWalletRequest request,
            @RequestHeader("X-User-Id") UUID userId) {


        log.info("Creating wallet for user: {}", userId);
        WalletResponse wallet = walletService.createWallet(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Wallet created successfully", wallet));
    }


    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<WalletResponse>>> listUserWallets(
            @RequestHeader("X-User-Id") UUID userId) {
        
        log.info("Fetching wallets for user: {}", userId);
        List<WalletResponse> wallets = walletService.getUserWallets(userId);
        return ResponseEntity.ok(ApiResponse.ok("Wallets retrieved successfully", wallets));
    }


    @PostMapping("/deposit/{walletId}")
    public ResponseEntity<ApiResponse<WalletResponse>> deposit(@Valid @RequestBody DepositRequest depositRequest,
                                                               @PathVariable UUID walletId) {

        WalletResponse deposit = walletService.deposit(walletId, depositRequest);
        log.info("Deposited {} to wallet {}", depositRequest.amount(), walletId);

        String message = String.format("Deposit of %s completed successfully into wallet %s",
                depositRequest.amount(), walletId);

        return ResponseEntity.ok(ApiResponse.ok(message, deposit));
    }





}
