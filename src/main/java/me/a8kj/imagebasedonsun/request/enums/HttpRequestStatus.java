package me.a8kj.imagebasedonsun.request.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpRequestStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private final int code;
    private final String description;

    public static HttpRequestStatus fromCode(int code) {
        for (HttpRequestStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
