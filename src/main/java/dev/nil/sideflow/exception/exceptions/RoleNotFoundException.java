package dev.nil.sideflow.exception.exceptions;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException {

    private final String role;

    public RoleNotFoundException(String role, String message) {
        super(message);
        this.role = role;
    }
}
