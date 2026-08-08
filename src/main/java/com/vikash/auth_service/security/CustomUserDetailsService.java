package com.vikash.auth_service.security;

import com.vikash.auth_service.entity.User;
import com.vikash.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    This class can return user by using email as username and load in customUserDetails that used by SpringSecurity
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with email : " + email));

        return new CustomUserDetails(user);
    }
}
