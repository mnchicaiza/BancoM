package com.bankM.transactions.services;


import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.model.AccountEntity;
import com.bankM.transactions.vo.AccountDTO;
import com.bankM.transactions.vo.AccountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    AccountResponse convertToAccountResponse(AccountEntity accountEntity);
    List<AccountResponse> findAll();
    AccountResponse saveNewAccount(AccountDTO accountDTO) throws EntityExistsException, EntityNoExistsException;
    List<AccountResponse> findAccountsByCi(String ci) throws EntityNoExistsException;
    AccountResponse updateAccount(Integer accountId, AccountDTO accountDTO) throws EntityNoExistsException;
    void deleteAccount(Integer accountId) throws EntityNoExistsException;
    AccountEntity findById(Integer accountId) throws EntityNoExistsException;
    void updateBalanceByAccount(Integer accountId, BigDecimal balance) throws EntityNoExistsException;
}
