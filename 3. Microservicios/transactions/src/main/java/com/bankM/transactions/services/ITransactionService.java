package com.bankM.transactions.services;


import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.exceptions.TransactionException;
import com.bankM.transactions.model.TransactionEntity;
import com.bankM.transactions.vo.Response;
import com.bankM.transactions.vo.TransactionDTO;
import com.bankM.transactions.vo.TransactionResponse;

import java.util.Date;
import java.util.List;

public interface ITransactionService {
    TransactionResponse convertToTransactionResponse(TransactionEntity transactionEntity);
    List<TransactionResponse> findAll();
    TransactionResponse saveNewTransaction(TransactionDTO transactionDTO) throws EntityExistsException, EntityNoExistsException, TransactionException;
    List<Response> findByDateAndAccountId(String ci, Date startDate, Date endDate) throws EntityNoExistsException, TransactionException;
    Response convertToResponse(TransactionEntity transactionEntity) throws EntityNoExistsException;
}
