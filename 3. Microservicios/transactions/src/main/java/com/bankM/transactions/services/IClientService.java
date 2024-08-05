package com.bankM.transactions.services;

import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.vo.ClientToTransactionResponse;

public interface IClientService {
    ClientToTransactionResponse findByCi(String ci) throws EntityNoExistsException;
    ClientToTransactionResponse findByClientId(Integer clientId) throws EntityNoExistsException;
}
