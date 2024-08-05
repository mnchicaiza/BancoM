package com.bankM.clients.repository;

import com.bankM.clients.model.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IClientRepository extends CrudRepository<ClientEntity, Integer> {
    Boolean existsByCi(String ci);
    Optional<ClientEntity> findByCiOrId(String ci, Integer id);
}
