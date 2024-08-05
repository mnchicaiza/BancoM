package com.bankM.transactions.repository;

import com.bankM.transactions.model.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAccountRepository extends CrudRepository<AccountEntity, Integer> {
    List<AccountEntity> findAllByClientId(Integer clientId);
}
