package com.bankM.clients.services;

import com.bankM.clients.exceptions.DataErrorException;
import com.bankM.clients.exceptions.EntityExistsException;
import com.bankM.clients.exceptions.EntityNoExistsException;
import com.bankM.clients.model.ClientEntity;
import com.bankM.clients.repository.IClientRepository;
import com.bankM.clients.vo.ClientDTO;
import com.bankM.clients.vo.ClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClientService implements IClientService{

    private final IClientRepository iClientRepository;

    @Override
    public List<ClientResponse> findAll() {
        List<ClientEntity> clientEntities = (List<ClientEntity>) iClientRepository.findAll();
        return clientEntities.stream().map(this::convertToClientResponse).collect(Collectors.toList());
    }

    @Override
    public ClientResponse convertToClientResponse(ClientEntity clientEntity) {
        return ClientResponse.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .ci(clientEntity.getCi())
                .gender(clientEntity.getGender())
                .age(clientEntity.getAge())
                .address(clientEntity.getAddress())
                .phone(clientEntity.getPhone())
                .build();
    }

    @Override
    public ClientResponse findBy(String ci, Integer id) throws EntityNoExistsException, DataErrorException {
        if(ci == null && id == null){
            throw new DataErrorException(HttpStatus.BAD_REQUEST, "No se ha ingresado ningun parametro");
        }
        return iClientRepository.findByCiOrId(ci, id)
                .map(this::convertToClientResponse)
                .orElseThrow(() -> new EntityNoExistsException(HttpStatus.BAD_REQUEST,"No existe"));
    }

    @Override
    public ClientResponse saveNewClient(ClientDTO clientDTO) throws EntityExistsException {
        Boolean ifExists=iClientRepository.existsByCi(clientDTO.getCi());
        if (ifExists) throw new EntityExistsException(HttpStatus.BAD_REQUEST,"Ya existe el cliente con la ci : " + clientDTO.getCi());
        ClientEntity newEntity = new ClientEntity();
        newEntity.setName(clientDTO.getName());
        newEntity.setCi(clientDTO.getCi());
        newEntity.setGender(clientDTO.getGender());
        newEntity.setAge(clientDTO.getAge());
        newEntity.setAddress(clientDTO.getAddress());
        newEntity.setPhone(clientDTO.getPhone());
        newEntity.setPassword(convertToBase64(clientDTO.getPassword()));
        iClientRepository.save(newEntity);
        return convertToClientResponse(newEntity);
    }

    private String convertToBase64(String password) {
        String encodePassword = " ";
        try {
            encodePassword = new String(Base64.getEncoder().encode(password.getBytes("UTF8")));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return encodePassword;
    }

    @Override
    public ClientResponse updateClient(Integer id, ClientDTO clientDTO) throws EntityNoExistsException {
        ClientEntity updatableEntity = findById(id);
        updatableEntity.setName(clientDTO.getName());
        updatableEntity.setCi(clientDTO.getCi());
        updatableEntity.setAge(clientDTO.getAge());
        updatableEntity.setGender(clientDTO.getGender());
        updatableEntity.setAddress(clientDTO.getAddress());
        updatableEntity.setPhone(clientDTO.getPhone());
        updatableEntity.setPassword(convertToBase64(clientDTO.getPassword()));
        iClientRepository.save(updatableEntity);
        return convertToClientResponse(updatableEntity);
    }

    @Override
    public void deleteClient(Integer id) throws EntityNoExistsException {
        findById(id);
        iClientRepository.deleteById(id);
    }

    @Override
    public ClientEntity findById(Integer id) throws EntityNoExistsException {
        Optional<ClientEntity> optionalClientEntity = iClientRepository.findById(id);
        if (optionalClientEntity.isEmpty()){
            throw new EntityNoExistsException(HttpStatus.BAD_REQUEST,"No existe: " + id);
        } else {
            return optionalClientEntity.get();
        }
    }
}
