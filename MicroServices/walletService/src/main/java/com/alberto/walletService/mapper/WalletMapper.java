package com.alberto.walletService.mapper;

import com.alberto.walletService.DTOs.WalletResponse;
import com.alberto.walletService.model.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse toResponse(Wallet wallet);

}

