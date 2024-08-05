package com.bankM.clients.services;

import com.bankM.clients.exceptions.DataErrorException;
import com.bankM.clients.vo.ClientDTO;
import com.bankM.clients.exceptions.EntityExistsException;
import com.bankM.clients.exceptions.EntityNoExistsException;
import com.bankM.clients.model.ClientEntity;
import com.bankM.clients.vo.ClientResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IClientService {
    ClientResponse convertToClientResponse(ClientEntity clientEntity);
    List<ClientResponse> findAll();
    ClientResponse saveNewClient(ClientDTO clientDTO) throws EntityExistsException, UnsupportedEncodingException;
    ClientResponse findBy(String ci, Integer id) throws EntityNoExistsException, DataErrorException;
    ClientResponse updateClient(Integer clientId, ClientDTO clientDTO) throws EntityNoExistsException;
    void deleteClient(Integer id) throws EntityNoExistsException;
    ClientEntity findById(Integer id) throws EntityNoExistsException;
}
