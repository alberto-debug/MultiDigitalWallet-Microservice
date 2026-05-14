package com.alberto.walletService.exception;

import com.alberto.walletService.DTOs.ApiResponse;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler extends RuntimeException {
    public GlobalExceptionHandler(String message) {
        super(message);
    }

    @ExceptionHandler(WalletAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleWalletAlreadyExists(WalletAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientBalanceException(InsufficientBalanceException ins){
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(ApiResponse.error(ins.getMessage()));
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateTransactionException(DuplicateTransactionException dt){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(dt.getMessage()));
    }

}
