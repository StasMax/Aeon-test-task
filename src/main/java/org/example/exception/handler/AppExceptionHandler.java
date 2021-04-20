package org.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.AuthenticationException;
import org.example.exception.ServiceException;
import org.example.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleCollectorException(HttpServletRequest req, ValidationException ex) {
        StringBuilder message = new StringBuilder();
        message.append(ex.getMessage()).append(" ");
        ex.getFieldErrors().forEach(fieldError -> message.append(fieldError.getDefaultMessage()).append(", "));
        log.error("{}\n{}", message, ex);
      ApiError error = ApiError.create(HttpStatus.BAD_REQUEST, ex, message.toString(), req.getRequestURL());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ApiError> handleCollectorException(HttpServletRequest req, ServiceException ex) {
        String message = ex.getMessage();
        log.error("{}\n{}", message, ex);
        ApiError error = ApiError.create(ex.getHttpStatus(), ex, message, req.getRequestURL());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> handleCollectorException(HttpServletRequest req, AuthenticationException ex) {
        String message = ex.getMessage();
        log.error("{}\n{}", message, ex);
        ApiError error = ApiError.create(HttpStatus.BAD_REQUEST, ex, message, req.getRequestURL());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
