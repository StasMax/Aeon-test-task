package org.example.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<FieldError> fieldErrors;


    private static final String DEFAULT_MESSAGE = "Validation field error";

    public ValidationException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public ValidationException(List<FieldError> fieldErrors) {
        super(DEFAULT_MESSAGE);
        this.fieldErrors = fieldErrors;
    }
}
