package dev.nil.sideflow.exception.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String email;

    public UserAlreadyExistsException(String email, String message) {
        super(message);
        this.email = email;
    }
}
