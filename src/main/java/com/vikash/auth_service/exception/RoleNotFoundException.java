package com.vikash.auth_service.exception;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}