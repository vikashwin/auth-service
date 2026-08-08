package com.vikash.auth_service.mapper;

import com.vikash.auth_service.dto.UserRequestDTO;
import com.vikash.auth_service.dto.UserResponseDTO;
import com.vikash.auth_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public final PasswordEncoder passwordEncoder;

    // DTO -> Entity
    public User toEntity(UserRequestDTO dto){

        return User.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .build();
    }

    // Entity -> DTO
    public UserResponseDTO toResponse(User user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .enabled(user.getEnabled())
                .emailVerified(user.getEmailVerified())
                .accountLocked(user.getAccountLocked())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // Update existing entity from DTO
    public void updateEntity(UserRequestDTO dto, User user){

        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
    }
}
