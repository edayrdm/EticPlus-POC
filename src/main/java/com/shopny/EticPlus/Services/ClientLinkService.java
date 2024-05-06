package com.shopny.EticPlus.Services;

import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Repositories.ClientLinkRepository;
import com.shopny.EticPlus.Repositories.CustomClientLinkRepositoryImp;
import com.shopny.EticPlus.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientLinkService {

    private static final Logger logger = LoggerFactory.getLogger(ClientLinkService.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public final ClientLinkRepository clientLinkRepository;
    public final CustomClientLinkRepositoryImp customClientLinkRepositoryImp;

    @Autowired
    public ClientLinkService(ClientLinkRepository clientLinkRepository,
                             CustomClientLinkRepositoryImp customClientLinkRepositoryImp) {
        this.clientLinkRepository = clientLinkRepository;
        this.customClientLinkRepositoryImp = customClientLinkRepositoryImp;
    }

    public ClientLink createClientLink(ClientLink client) {
        return clientLinkRepository.save(client);
    }

    public List<ClientLink> createClientLinkAll(List<ClientLink> clientList) {
        return  clientLinkRepository.saveAll(clientList);
    }

    public void deleteClientLinkById(String id) {
        clientLinkRepository.deleteByClientId(id);
    }

    public void permissionForLink(Client client){
        int activeLinkCount =  clientLinkRepository.countByClientIdAndStatement(client.getId(), true);
        if( activeLinkCount >= 3 && !Objects.equals(client.getAccount(), "Platinum") )
            throw new UserNotFoundException("Client is not Premium");
    }

    public List<ClientLinkDto> getClientLinkByClient(Client client) {

        List<ClientLink> list = clientLinkRepository.findByClientId(client.getId())
                .orElseThrow(() -> new UserNotFoundException("Client Link List not found"));

        List<ClientLinkDto> result = list.stream().map(clientLink -> ClientLinkDto.builder()
                        .clientName(client.getName())
                        .linkName(clientLink.getLinkId())
                        .statement(clientLink.getStatement())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    public ClientLinkDto updateClientLinkStatus(Client client, Link link, Boolean statement) {

        ClientLink clientLink =customClientLinkRepositoryImp.updateClientLink(client, link, statement);

        logger.info("{} activated {} :: Execution Time - {}", client.getName(), link.getName(), dateTimeFormatter.format(LocalDateTime.now()));

        return ClientLinkDto.builder()
                .clientName(clientLink.getClientId())
                .linkName(clientLink.getLinkId())
                .statement(clientLink.getStatement())
                .build();
    }
}
