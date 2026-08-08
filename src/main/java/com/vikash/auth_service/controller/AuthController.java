package com.vikash.auth_service.controller;

import com.vikash.auth_service.dto.AuthResponseDTO;
import com.vikash.auth_service.dto.RefreshTokenRequestDTO;
import com.vikash.auth_service.dto.UserLoginDTO;
import com.vikash.auth_service.security.CustomUserDetails;
import com.vikash.auth_service.security.RefreshTokenService;
import com.vikash.auth_service.service.AuthService;
import com.vikash.auth_service.dto.UserRequestDTO;
import com.vikash.auth_service.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO userRequestDTO){
        return authService.register(userRequestDTO);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return authService.login(userLoginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        refreshTokenService.revoke(userDetails.getUser());
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/refresh")
    public AuthResponseDTO refresh(@RequestBody RefreshTokenRequestDTO request){
        return authService.refresh(request);
    }





}
