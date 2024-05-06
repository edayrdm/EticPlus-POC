package com.shopny.EticPlus;

import com.shopny.EticPlus.DTOs.ClientDto;
import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Repositories.ClientRepository;
import com.shopny.EticPlus.Services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void create_shouldCreateNewClient() {
        // Arrange

        String clientId = "abc123";
        Client mockClient = new Client();
        mockClient.setId(clientId);
        mockClient.setName("John Doe");
        mockClient.setPassword("psw123");
        mockClient.setAccount("Silver");

        // Mock the behavior of the repository to return the mock employee
        Mockito.when(clientRepository.save(any())).thenReturn(mockClient);

        // Act
        ClientDto req = new ClientDto();
        Client result = clientService.createClient(req);

        // Assert
        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("Silver", result.getAccount());
    }
}
