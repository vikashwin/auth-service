package com.vikash.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY) //user login with multiple device
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    private boolean revoked;
}