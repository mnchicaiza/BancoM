package com.bankM.transactions.services;

import com.bankM.transactions.client.IClientClient;
import com.bankM.transactions.exceptions.EntityNoExistsException;
import com.bankM.transactions.vo.ClientToTransactionResponse;
import feign.FeignException;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientService implements IClientService {

    private final IClientClient iClientClient;

    @Override
    public ClientToTransactionResponse findByCi(String ci) throws EntityNoExistsException {
        ResponseEntity<ClientToTransactionResponse> clientResponse;
        try {
            clientResponse = iClientClient.findBy(null, ci);
        } catch (RetryableException e){
            throw new EntityNoExistsException(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo consultar datos de Cliente");
        } catch (FeignException e){
            e.printStackTrace();
            throw new EntityNoExistsException(HttpStatus.BAD_REQUEST,
                    "La cedula ingresada no corresponde a ningun cliente, crear o verificar cedula: " + ci);
        }
        return clientResponse.getBody();
    }

    @Override
    public ClientToTransactionResponse findByClientId(Integer clientId) throws EntityNoExistsException {
        ResponseEntity<ClientToTransactionResponse> clientResponse = iClientClient.findBy(clientId,null);
        if(!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new EntityNoExistsException(HttpStatus.BAD_REQUEST,
                    "No existe cliente : " + clientId);
        }
        return clientResponse.getBody();
    }
}
