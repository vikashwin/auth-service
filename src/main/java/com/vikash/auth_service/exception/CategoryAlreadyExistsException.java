package com.vikash.auth_service.exception;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
