package com.vikash.auth_service.service;

import com.vikash.auth_service.dto.AuthResponseDTO;
import com.vikash.auth_service.dto.RefreshTokenRequestDTO;
import com.vikash.auth_service.dto.UserLoginDTO;
import com.vikash.auth_service.entity.RefreshToken;
import com.vikash.auth_service.dto.UserRequestDTO;
import com.vikash.auth_service.dto.UserResponseDTO;
import com.vikash.auth_service.entity.Role;
import com.vikash.auth_service.entity.User;
import com.vikash.auth_service.enums.UserRole;
import com.vikash.auth_service.exception.RoleNotFoundException;
import com.vikash.auth_service.exception.UserAlreadyExistsException;
import com.vikash.auth_service.mapper.UserMapper;
import com.vikash.auth_service.repository.RefreshTokenRepository;
import com.vikash.auth_service.repository.RoleRepository;
import com.vikash.auth_service.repository.UserRepository;
import com.vikash.auth_service.security.CustomUserDetails;
import com.vikash.auth_service.security.JwtService;
import com.vikash.auth_service.security.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository repository;

    public UserResponseDTO register(UserRequestDTO userRequestDTO){

        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + userRequestDTO.getEmail()
            );
        }
        User user = userMapper.toEntity(userRequestDTO);

        Role customerRole = roleRepository.findByName(UserRole.CUSTOMER)
                .orElseThrow(() -> new RoleNotFoundException("Customer role not found"));
        user.getRoles().add(customerRole);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);    //Map userEntity to userResponseDTO and return userResponseDTO

    }

    public AuthResponseDTO login(UserLoginDTO userLoginDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        User user = userRepository.findUserByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        RefreshToken refreshToken = refreshTokenService.create(user);
        String accessToken = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();
    }

    @Transactional
    public AuthResponseDTO refresh(RefreshTokenRequestDTO request){

        RefreshToken oldToken =
                refreshTokenService.verify(request.getRefreshToken());

        User user = oldToken.getUser();

        repository.delete(oldToken);

        RefreshToken newToken = refreshTokenService.create(user);

        String accessToken =
                jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();
    }




}
