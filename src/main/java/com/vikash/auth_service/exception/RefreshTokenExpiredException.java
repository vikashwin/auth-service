package com.vikash.auth_service.exception;

public class RefreshTokenExpiredException extends RuntimeException{

    public RefreshTokenExpiredException(String message){
        super(message);
    }
}
