package com.vikash.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;


}
