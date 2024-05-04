package com.shopny.EticPlus.Services;

import com.shopny.EticPlus.DTOs.ClientDto;
import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Repositories.ClientLinkRepository;
import com.shopny.EticPlus.Repositories.ClientRepository;
import com.shopny.EticPlus.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // function to create a new client
    public Client createClient(ClientDto clientDto) {

        Client client = Client.builder()
                .name(clientDto.getName())
                .details(clientDto.getDetails())
                .password(clientDto.getPassword())
                .account(clientDto.getAccount())
                .build();

        return clientRepository.save(client);
    }

    public Client getClientByIdAndPassword(String name, String password) {
        return clientRepository.findByNameAndPassword(name, password)
                .orElseThrow(() -> new UserNotFoundException("Username or password is not correct"));

    }

    public Client getClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Client is not found"));

    }

    public List<Client> getAllClientData() {
        return clientRepository.findAll();
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }
}
