package com.alberto.integration;

import com.alberto.DTOs.ApiLoginResponseDTO;
import com.alberto.DTOs.LoginRequest;
import com.alberto.DTOs.LoginResponse;
import com.alberto.DTOs.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationClient {

    private final WebClient webClient;

    public AuthenticationClient(WebClient.Builder builder, @Value("${AUTH_SERVICE_URL:http://localhost:8081}") String authServiceUrl) {
        this.webClient = builder
                .baseUrl(authServiceUrl)
                .build();
    }

    public LoginResponse response(String email, String password) throws Exception {
        LoginRequest request = new LoginRequest(email, password);

        return webClient.post()
                .uri("/auth/login")
                .bodyValue(request)
                .retrieve()
                .toEntity(ApiLoginResponseDTO.class)
                .map(entity -> {
                    String authHeader = entity.getHeaders().getFirst("Authorization");
                    String token = null;
                    
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        token = authHeader.substring(7);
                    }
                    
                    ApiLoginResponseDTO apiResponse = entity.getBody();
                    if (apiResponse == null || !apiResponse.success() || apiResponse.data() == null) {
                        throw new RuntimeException("Invalid response from auth service");
                    }
                    
                    if (token == null || token.isBlank()) {
                        throw new RuntimeException("Invalid response from auth service: missing Authorization header");
                    }
                    
                    return new LoginResponse(token, apiResponse.data());
                })
                .onErrorMap(ex -> new RuntimeException("Authentication service error: " + ex.getMessage(), ex))
                .block();
    }

    public ApiLoginResponseDTO register(String name, String email, String password) throws Exception{

        RegisterRequestDTO requestDTO = new RegisterRequestDTO(name, email, password);

        return webClient.post()
                .uri("/auth/register")
                .bodyValue(requestDTO)
                .retrieve()
                .toEntity(ApiLoginResponseDTO.class)
                .map( entity -> {
                    ApiLoginResponseDTO apiResponse = entity.getBody();

                    if (apiResponse == null || !apiResponse.success() || apiResponse.data() == null) {
                        throw new RuntimeException("Invalid response from auth service");
                    }
                    return apiResponse;


                } )    .onErrorMap(ex ->
                        new RuntimeException("Register service error: " + ex.getMessage(), ex)
                )
                .block();
    }


}
