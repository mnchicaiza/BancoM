package com.bankM.clients.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EntityExistsException extends Exception{
    private HttpStatus httpStatus;
    private String message;
}

