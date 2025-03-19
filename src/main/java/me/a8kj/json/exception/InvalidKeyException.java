package me.a8kj.json.exception;

import lombok.NonNull;

public class InvalidKeyException extends RuntimeException {

    public InvalidKeyException(@NonNull String message) {
        super(message);
    }

    public InvalidKeyException(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
    }
}
