package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.enums.ResponseStatus;
import com.pesupal.server.exceptions.BaseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseDto> handleBaseException(BaseException ex) {

        return ResponseEntity.status(ex.getHttpStatus()).body(new ApiResponseDto(ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiResponseDto> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("Invalid data access usage: " + ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("Validation errors occurred.", errors, ResponseStatus.FAILURE));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto> handleBadCredentialsException(BadCredentialsException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto("Invalid credentials: " + ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("Data integrity violation: " + ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("Missing request parameter: " + ex.getParameterName(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleFileNotFoundException(FileNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseDto> handleNullPointerException(NullPointerException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto(ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("Malformed JSON request: " + ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGenericException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto(ex.getMessage(), ResponseStatus.FAILURE));
    }
}
