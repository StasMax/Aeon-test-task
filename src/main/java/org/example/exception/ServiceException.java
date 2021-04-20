package org.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException{

    private HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ServiceException(String message, HttpStatus httpStatus, Throwable e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }
}
