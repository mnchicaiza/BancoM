package com.bankM.transactions.repository;

import com.bankM.transactions.model.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ITransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    @Query("""
        select
           t
        from
            TransactionEntity t
        join AccountEntity a on
            a.accountId = t.accountId
        where
            a.clientId = :clientId
            and t.date >= :startDate
            and t.date < :endDate
        order by t.date desc
    """)
    List<TransactionEntity> findAllByClientIdAndBetweenDates(Integer clientId, Date startDate, Date endDate);
}
