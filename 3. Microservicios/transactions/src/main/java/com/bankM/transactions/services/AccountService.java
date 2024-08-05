package com.bankM.transactions.services;

import com.bankM.transactions.client.IClientClient;
import com.bankM.transactions.exceptions.EntityExistsException;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.model.AccountEntity;
import com.bankM.transactions.repository.IAccountRepository;
import com.bankM.transactions.vo.AccountDTO;
import com.bankM.transactions.vo.AccountResponse;
import com.bankM.transactions.vo.ClientToTransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccountService implements IAccountService{

    private final IAccountRepository iAccountRepository;

    private final IClientClient iClientClient;

    private final IClientService iClientService;

    @Override
    public List<AccountResponse> findAll() {
        List<AccountEntity> accountEntities = (List<AccountEntity>) iAccountRepository.findAll();
        return accountEntities.stream().map(this::convertToAccountResponse).collect(Collectors.toList());
    }

    @Override
    public AccountResponse convertToAccountResponse(AccountEntity accountEntity) {
        ClientToTransactionResponse clientResponse = iClientClient.findBy(accountEntity.getClientId(),null).getBody();
        return AccountResponse.builder()
                .accountId(accountEntity.getAccountId())
                .nameClient(clientResponse.getName())
                .typeAccount(accountEntity.getTypeAccount())
                .balance(accountEntity.getBalance())
                .status(Objects.equals(accountEntity.getStatus(), "1") ? "ACTIVO" : "INACTIVO")
                .build();
    }

    @Override
    public List<AccountResponse> findAccountsByCi(String ci) {
        ClientToTransactionResponse clientResponse = iClientClient.findBy(null,ci).getBody();
        return iAccountRepository.findAllByClientId(clientResponse.getId()).stream()
                .map(this::convertToAccountResponse).collect(Collectors.toList());
    }

    @Override
    public AccountResponse saveNewAccount(AccountDTO accountDTO) throws EntityExistsException, EntityNoExistsException {
        ClientToTransactionResponse clientResponse = iClientService.findByCi(accountDTO.getCi());
        AccountEntity newEntity = new AccountEntity();
        newEntity.setTypeAccount(accountDTO.getTypeAccount());
        newEntity.setClientId(clientResponse.getId());
        newEntity.setBalance(BigDecimal.ZERO);
        iAccountRepository.save(newEntity);
        return convertToAccountResponse(newEntity);
    }

    @Override
    public AccountResponse updateAccount(Integer accountId, AccountDTO accountDTO) throws EntityNoExistsException {
        AccountEntity updatableEntity = findById(accountId);
        updatableEntity.setAccountId(accountId);
        updatableEntity.setTypeAccount(accountDTO.getTypeAccount());
        updatableEntity.setStatus(accountDTO.getStatus());
        iAccountRepository.save(updatableEntity);
        return convertToAccountResponse(updatableEntity);
    }

    @Override
    public void deleteAccount(Integer accountId) throws EntityNoExistsException {
        findById(accountId);
        iAccountRepository.deleteById(accountId);
    }

    @Override
    public AccountEntity findById(Integer accountId) throws EntityNoExistsException {
        Optional<AccountEntity> optionalAccountEntity = iAccountRepository.findById(accountId);
        if (optionalAccountEntity.isEmpty()){
            throw new EntityNoExistsException(HttpStatus.BAD_REQUEST,"No existe: " + accountId);
        } else {
            return optionalAccountEntity.get();
        }
    }

    @Override
    public void updateBalanceByAccount(Integer accountId, BigDecimal balance) throws EntityNoExistsException {
        AccountEntity account = findById(accountId);
        account.setBalance(balance);
        iAccountRepository.save(account);
    }
}
