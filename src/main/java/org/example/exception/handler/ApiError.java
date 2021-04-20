package org.example.exception.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Кастомный ответ для ошибок.
 */
@Getter
@Setter
public class ApiError {

    private static final String DEFAULT_MESSAGE = "Что-то пошло не так";

    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private String errors;
    private String exception;
    private String path;

    public static ApiError create(HttpStatus status, Exception ex, String message, StringBuffer requestUrl) {
        String path = null;
        if (requestUrl != null) {
            path = requestUrl.toString();
        }
        ApiError apiError = new ApiError();
        apiError.setTimestamp(ZonedDateTime.now());

        if (message == null || message.trim().equals("")) {
            apiError.setMessage(DEFAULT_MESSAGE);
        } else {
            apiError.setMessage(message);
        }

        apiError.setStatus(status.value());
        apiError.setException(ex.getClass().getName());
        apiError.setPath(path);
        apiError.setErrors(status.getReasonPhrase());
        return apiError;
    }

}
