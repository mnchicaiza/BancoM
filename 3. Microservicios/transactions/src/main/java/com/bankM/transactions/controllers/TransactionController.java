package com.bankM.transactions.controllers;


import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.exceptions.TransactionException;
import com.bankM.transactions.services.ITransactionService;
import com.bankM.transactions.vo.Response;
import com.bankM.transactions.vo.TransactionDTO;
import com.bankM.transactions.vo.TransactionResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final ITransactionService iTransactionService;

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionResponse>> findAll(){
        return new ResponseEntity<List<TransactionResponse>>(iTransactionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/transaction/reportes/{ci}")
    public ResponseEntity<List<Response>> findByIdAndDate(
            @RequestParam
            @Valid
            @NotNull
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam
            @Valid
            @NotNull
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @PathVariable("ci") String ci) throws EntityNoExistsException, TransactionException {
        return new ResponseEntity<List<Response>>(iTransactionService.findByDateAndAccountId(ci,startDate,endDate), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> saveNewTransaction(@Valid @RequestBody TransactionDTO body) throws EntityExistsException, EntityNoExistsException, TransactionException {
        return new ResponseEntity<>(iTransactionService.saveNewTransaction(body), HttpStatus.CREATED);
    }

}
