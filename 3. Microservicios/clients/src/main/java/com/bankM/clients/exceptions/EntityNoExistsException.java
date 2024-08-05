package com.bankM.clients.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class EntityNoExistsException extends Exception{
    private HttpStatus httpStatus;
    private String message;

    public EntityNoExistsException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

