package com.vikash.auth_service.dto;

import com.vikash.auth_service.enums.UserGender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {


    //Password is not present
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private UserGender gender;

    private Boolean enabled;

    private Boolean emailVerified;

    private Boolean accountLocked;

    private LocalDateTime createdAt;
}
