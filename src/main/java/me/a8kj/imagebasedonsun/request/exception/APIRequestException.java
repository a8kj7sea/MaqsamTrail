package me.a8kj.imagebasedonsun.request.exception;

public class APIRequestException extends Exception {

    public APIRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIRequestException(String message) {
        super(message);
    }
}
