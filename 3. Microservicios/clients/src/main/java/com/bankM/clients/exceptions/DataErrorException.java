package com.bankM.clients.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataErrorException extends Exception{
    private HttpStatus httpStatus;
    private String message;

    public DataErrorException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

