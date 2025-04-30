package dev.nil.sideflow.exception;

import dev.nil.sideflow.exception.exceptions.RoleNotFoundException;
import dev.nil.sideflow.exception.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(UserAlreadyExistsException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                                 .message(e.getMessage())
                                 .status(HttpStatus.BAD_REQUEST.value())
                                 .data(e.getEmail())
                                 .build());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(RoleNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.builder()
                                 .message(e.getMessage())
                                 .status(HttpStatus.NOT_FOUND.value())
                                 .data(e.getRole())
                                 .build());
    }
}
