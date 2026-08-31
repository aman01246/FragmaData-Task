package com.oauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oauth.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // RESOURCE NOT FOUND
    // ===============================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiResponse<>(
                                HttpStatus.NOT_FOUND.value(),
                                exception.getMessage(),
                                null
                        )
                );
    }


    // ===============================
    // BAD REQUEST
    // ===============================

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            BadRequestException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                exception.getMessage(),
                                null
                        )
                );
    }


    // ===============================
    // ILLEGAL ARGUMENT
    // ===============================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                HttpStatus.BAD_REQUEST.value(),
                                exception.getMessage(),
                                null
                        )
                );
    }


    // ===============================
    // GENERAL EXCEPTION
    // ===============================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception exception
    ) {

        // Don't expose internal exception details in production
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ApiResponse<>(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Something went wrong",
                                null
                        )
                );
    }
}