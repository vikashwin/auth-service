package com.vikash.auth_service.repository;

import com.vikash.auth_service.entity.RefreshToken;
import com.vikash.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("""
            SELECT rt
            FROM RefreshToken rt
            JOIN FETCH rt.user
            WHERE rt.token = :token
            """)
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUser(User user);

    void deleteByUser(User user);

    void deleteByUserId(Long userId);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);

    Optional<RefreshToken> findByUser(User user);
}