package com.vikash.auth_service.util;

import com.vikash.auth_service.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !(authentication.getPrincipal()
                instanceof CustomUserDetails user)) {
            return null;

        }
        return user;
    }

    public static Long getCurrentUserId() {
        CustomUserDetails user = getCurrentUser();
        return user == null ? null : user.getId();

    }

    public static String getCurrentEmail() {
        CustomUserDetails user = getCurrentUser();
        return user == null ? null : user.getEmail();

    }

    public static boolean hasRole(String role) {
        Authentication authentication = getAuthentication();
        if (authentication == null)
            return false;

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_" + role));

    }

}