package com.vikash.auth_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

//DispatcherServlet search best fit ExceptionHandler

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();   // <-- add this

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.toString())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }


    @ExceptionHandler(ConstraintViolationException.class) //The exception throw during hibernate validate field during saving data
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request){

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // The exception throw when use @valid and validate field on controller level
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request){

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponse> handleArithmeticException(
            ArithmeticException ex ,
            HttpServletRequest request){

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Invalid email or password")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenExpired(
            RefreshTokenExpiredException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshToken(
            InvalidRefreshTokenException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(
            UsernameNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("You don't have permission to access this resource.")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(org.springframework.security.authentication.DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabled(
            DisabledException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Your account is disabled.")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.security.authentication.LockedException.class)
    public ResponseEntity<ErrorResponse> handleLocked(
            LockedException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Your account is locked.")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(
            CategoryAlreadyExistsException ex,
            HttpServletRequest request
    ){

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response , HttpStatus.CONFLICT);
    }



}