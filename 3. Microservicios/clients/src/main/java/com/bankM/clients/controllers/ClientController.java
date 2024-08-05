package com.bankM.clients.controllers;

import com.bankM.clients.exceptions.DataErrorException;
import com.bankM.clients.exceptions.EntityExistsException;
import com.bankM.clients.exceptions.EntityNoExistsException;
import com.bankM.clients.services.IClientService;
import com.bankM.clients.vo.ClientDTO;
import com.bankM.clients.vo.ClientResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {

    private final IClientService iClientService;

    @GetMapping("/client")
    public ResponseEntity<List<ClientResponse>> findAll(){
        return new ResponseEntity<List<ClientResponse>>(iClientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/client/findBy")
    public ResponseEntity<ClientResponse> findBy(
            @RequestParam(value = "ci", required = false) String ci,
            @RequestParam(value = "id", required = false) Integer id) throws EntityNoExistsException, DataErrorException {
        return new ResponseEntity<ClientResponse>(iClientService.findBy(ci, id), HttpStatus.OK);
    }

    @PostMapping("/client")
    public ResponseEntity<ClientResponse> saveNewClient(@Valid @RequestBody ClientDTO body) throws EntityExistsException, UnsupportedEncodingException {
        return new ResponseEntity<>(iClientService.saveNewClient(body), HttpStatus.CREATED);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable("id") @Valid Integer clientId, @RequestBody @Valid ClientDTO body) throws EntityNoExistsException {
        return new ResponseEntity<>(iClientService.updateClient(clientId,body), HttpStatus.OK);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity deleteByClientId(@PathVariable("id") Integer clientId) throws EntityNoExistsException {
        iClientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
