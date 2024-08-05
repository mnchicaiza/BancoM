package com.bankM.transactions.exceptions;

import com.bankM.transactions.vo.ErrorDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorDTO> runtimeTransactionExceptionHandler(TransactionException e){
        log.error("ERROR EN TRANSACCION error: {} ", e.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(e.getHttpStatus().value(),e.getMessage());
        return new ResponseEntity<>(errorDTO,e.getHttpStatus());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity runtimeExceptionHandler(EntityExistsException e){
        log.error("ERROR ENTIDAD EXISTENTE error: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }

    @ExceptionHandler(EntityNoExistsException.class)
    public ResponseEntity runtimeNoExceptionHandler(EntityNoExistsException e){
        log.error("ERROR ENTIDAD NO EXISTENTE error: {} ", e.getMessage());
        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> errorFieldsHandler(MethodArgumentNotValidException eList){
        List<String> aux = new ArrayList<>();
        for (FieldError each : eList.getFieldErrors()) {
            aux.add("Error en " + each.getField() + " - " + each.getDefaultMessage());
            log.info("error en {}, rejected: {} ", each.getField() , each.getRejectedValue());
        }
        return new ResponseEntity<List<String>>(aux,HttpStatus.BAD_REQUEST);
    }
}
