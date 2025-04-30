package dev.nil.sideflow.exception;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ApiResponse<T> {

    private final Instant timestamp;
    private final int status;
    private final String message;
    private final T data;

    public ApiResponse(Instant timestamp, int status, String message, T data) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static void main(String[] args) {

        List<String> list;

    }
}
