package com.bankM.transactions.controllers;


import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.services.IAccountService;
import com.bankM.transactions.vo.AccountDTO;
import com.bankM.transactions.vo.AccountResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountController {

    private final IAccountService iAccountService;

    @GetMapping("/account")
    public ResponseEntity<List<AccountResponse>> findAll(){
        return new ResponseEntity<List<AccountResponse>>(iAccountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/account/{ci}")
    public ResponseEntity<List<AccountResponse>> findAccountsByCi(@PathVariable("ci") String ci) throws EntityNoExistsException {
        return new ResponseEntity<List<AccountResponse>>(iAccountService.findAccountsByCi(ci), HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity<AccountResponse> saveNewAccount(@Valid @RequestBody AccountDTO body) throws EntityExistsException, EntityNoExistsException {
        return new ResponseEntity<>(iAccountService.saveNewAccount(body), HttpStatus.CREATED);
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") @Valid Integer accountId, @RequestBody @Valid AccountDTO body) throws EntityNoExistsException {
        return new ResponseEntity<>(iAccountService.updateAccount(accountId,body), HttpStatus.OK);
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity deleteByAccountId(@PathVariable("id") Integer accountId) throws EntityNoExistsException {
        iAccountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
