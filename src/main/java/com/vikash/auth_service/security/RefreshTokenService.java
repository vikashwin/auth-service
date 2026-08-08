package com.vikash.auth_service.security;

import com.vikash.auth_service.entity.RefreshToken;
import com.vikash.auth_service.entity.User;
import com.vikash.auth_service.exception.InvalidRefreshTokenException;
import com.vikash.auth_service.exception.RefreshTokenExpiredException;
import com.vikash.auth_service.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${refresh-token.expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository repository;

    /**
     * Creates a new refresh token for the given user.
     * If the user already has a refresh token, it is removed
     * to ensure only one active refresh token exists per user.
     */
    public RefreshToken create(User user) {

        // Remove any existing refresh token for the user
        repository.findByUser(user).ifPresent(repository::delete);
        RefreshToken token = RefreshToken.builder()
                // Generate a random unique token
                .token(UUID.randomUUID().toString())
                // Associate token with the authenticated user
                .user(user)
                .expiryDate(LocalDateTime.now().plusSeconds(refreshExpiration / 1000))
                .revoked(false)
                .build();

        return repository.save(token);
    }

    /**
     * Validates the refresh token.
     * Checks:
     * 1. Token exists.
     * 2. Token has not been revoked or invalid.
     * 3. Token has not expired.
     * Returns the token if it is valid.
     */
    public RefreshToken verify(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Refresh token revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh token expired");
        }

        return refreshToken;
    }

    /**
     * Revokes the user's refresh token.
     * This is typically called during logout so the token
     * can no longer be used to obtain new access tokens.
     */
    public void revoke(User user) {
        repository.findByUser(user)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    repository.save(token);

                });
    }

}