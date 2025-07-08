package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.enums.ResponseStatus;
import com.pesupal.server.exceptions.BaseException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

        return ResponseEntity.badRequest().body(new ApiResponseDto("Invalid data access usage: " + ex.getMessage(), ResponseStatus.FAILURE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ApiResponseDto("Validation errors occurred.", errors, ResponseStatus.FAILURE));
    }
}
