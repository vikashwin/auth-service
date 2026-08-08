package com.vikash.auth_service.dto;

import com.vikash.auth_service.enums.UserGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;

    @Email(message = "Please enter a valid email")
    @NotBlank
    private String email;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private UserGender gender;
}
