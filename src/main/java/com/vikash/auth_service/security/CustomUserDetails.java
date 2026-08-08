package com.vikash.auth_service.security;

import com.vikash.auth_service.entity.User;
import com.vikash.auth_service.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//Spring Security never directly understands your User entity.
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // login using email
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public User getUser(){
        return user;
    }

    public String getEmail() {
        return  user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }

    public Set<UserRole> getRoles(){

        return user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
    }

}
