package com.bankM.transactions.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TransactionException extends Exception{
    private HttpStatus httpStatus;
    private String message;
}

