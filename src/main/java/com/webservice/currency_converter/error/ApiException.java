package com.webservice.currency_converter.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String fieldName;

    public ApiException (HttpStatus httpStatus, String fieldName, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.fieldName = fieldName;
    }
}
