package com.bankM.transactions.services;

import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.exceptions.TransactionException;
import com.bankM.transactions.model.AccountEntity;
import com.bankM.transactions.model.TransactionEntity;
import com.bankM.transactions.model.enums.TransactionTypes;
import com.bankM.transactions.repository.ITransactionRepository;
import com.bankM.transactions.utils.DateUtil;
import com.bankM.transactions.vo.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionService implements ITransactionService{

    private final ITransactionRepository iTransactionRepository;

    private final IAccountService iAccountService;

    private final IClientService iClientService;

    @Override
    public List<TransactionResponse> findAll() {
        List<TransactionEntity> transactionEntities = (List<TransactionEntity>) iTransactionRepository.findAll();
        return transactionEntities.stream().map(this::convertToTransactionResponse).collect(Collectors.toList());
    }

    @Override
    public TransactionResponse convertToTransactionResponse(TransactionEntity transactionEntity) {
        return TransactionResponse.builder()
                .transactionId(transactionEntity.getTransactionId())
                .accountId(transactionEntity.getAccountId())
                .typeAccount(transactionEntity.getAccountEntity().getTypeAccount())
                .balanceFinal(transactionEntity.getAccountEntity().getBalance())
                .amount(transactionEntity.getAmount())
                .type(transactionEntity.getType())
                .build();
    }

    @Override
    public TransactionResponse saveNewTransaction(TransactionDTO transactionDTO) throws EntityExistsException, EntityNoExistsException, TransactionException {
        if(transactionDTO.getAmount().compareTo(BigDecimal.ZERO) == 0){
            throw new TransactionException(HttpStatus.BAD_REQUEST, "La cantidad ingresada no puede ser 0 para realizar la transaccion");
        }
        AccountEntity account = iAccountService.findById(transactionDTO.getAccountId());
        TransactionEntity newEntity = new TransactionEntity();
        newEntity.setAccountId(transactionDTO.getAccountId());
        newEntity.setDate(new Date());
        newEntity.setAmount(transactionDTO.getAmount());
        final BigDecimal balance = account.getBalance();
        newEntity.setBalancePrevious(balance);
        newEntity.setBalanceFinal(newEntity.getBalancePrevious().add(transactionDTO.getAmount()));
        newEntity.setType(transactionDTO.getAmount().compareTo(BigDecimal.ZERO) < 0 ? TransactionTypes.DEBITO : TransactionTypes.CREDITO);
        newEntity.setAccountEntity(account);

        if(newEntity.getBalanceFinal().compareTo(BigDecimal.ZERO) < 0){
            throw new TransactionException(HttpStatus.BAD_REQUEST, "Saldo No Disponible");
        }
        iAccountService.updateBalanceByAccount(transactionDTO.getAccountId(), newEntity.getBalanceFinal());
        iTransactionRepository.save(newEntity);
        return convertToTransactionResponse(newEntity);
    }


    @Override
    public List<Response> findByDateAndAccountId(String ci, Date startDate, Date endDate) throws EntityNoExistsException, TransactionException {
        DateUtil.validateDates(startDate, endDate);
        ClientToTransactionResponse clientResponse = iClientService.findByCi(ci);
        List<TransactionEntity> transactionEntities = iTransactionRepository.findAllByClientIdAndBetweenDates(clientResponse.getId(),startDate, DateUtil.dateToEndOfDay(endDate));
        return transactionEntities.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public Response convertToResponse(TransactionEntity transactionEntity) {
        String name;
        try {
            ClientToTransactionResponse clientResponse = iClientService.findByClientId(transactionEntity.getAccountEntity().getClientId());
            name = clientResponse.getName();
        }
        catch (EntityNoExistsException e){
            name = "";
        }
        return Response.builder()
                .date(DateUtil.changeDateToLocalDate(transactionEntity.getDate()))
                .nameClient(name)
                .accountId(transactionEntity.getAccountId())
                .typeAccount(transactionEntity.getAccountEntity().getTypeAccount())
                .balancePrevious(transactionEntity.getBalancePrevious())
                .amount(transactionEntity.getAmount())
                .balanceFinal(transactionEntity.getBalanceFinal())
                .type(transactionEntity.getType())
                .build();
    }
}
