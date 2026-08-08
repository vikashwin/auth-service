package com.vikash.auth_service.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    @Email(message = "Please enter a valid email")
    @NotBlank
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;


}
