package com.alberto.multidigitalwallet.user_service.controller;


import com.alberto.multidigitalwallet.user_service.DTOs.*;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import com.alberto.multidigitalwallet.user_service.security.TokenService;
import com.alberto.multidigitalwallet.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request){

        User user = userService.register(request.name(), request.email(), request.password());
        String token = tokenService.generateToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(ApiResponse.ok("User registered successfully", UserResponseDTO.from(user)));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO ){

        User user = userService.authenticate(requestDTO.email(), requestDTO.password());
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(ApiResponse.ok("Login successful", UserResponseDTO.from(user)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.ok(UserResponseDTO.from(userService.findById(id))));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<UserResponseDTO>> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(ApiResponse.ok(UserResponseDTO.from(userService.findByEmail(email))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted successfully", null));
    }
    

}
