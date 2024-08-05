package com.bankM.clients.services;

import com.bankM.clients.exceptions.EntityExistsException;
import com.bankM.clients.model.ClientEntity;
import com.bankM.clients.repository.IClientRepository;
import com.bankM.clients.vo.ClientDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private IClientRepository iClientRepository;
    @InjectMocks
    private ClientService clientService;

    @Test
    void saveNewClient() throws EntityExistsException {
        ClientEntity client = new ClientEntity();
        client.setName("Maria Perez");
        client.setPassword("123456");

        ClientDTO clientDTO = ClientDTO.builder()
                .name("Maria Perez")
                .ci("1727300345")
                .age(45)
                .gender("Femenino")
                .address("Aloag")
                .phone("0987565829")
                .password("123456")
                .build();

        when(iClientRepository.existsByCi(any())).thenReturn(false);
        when(iClientRepository.save(any())).thenReturn(client);

        clientService.saveNewClient(clientDTO);

        ArgumentCaptor<ClientEntity> captor = ArgumentCaptor.forClass(ClientEntity.class);
        verify(iClientRepository).save(captor.capture());
        ClientEntity savedEntity = captor.getValue();

        assertNotEquals("123456", savedEntity.getPassword());
    }
}