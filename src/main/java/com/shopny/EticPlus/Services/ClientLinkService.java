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

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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

    public ClientLink createClientLink(ClientLink cl) {
        return clientLinkRepository.save(cl);
    }

    public void deleteClientLinkById(String id) {
        clientLinkRepository.deleteByClientId(id);
    }

    public List<ClientLinkDto> getClientLinkByClient(Client cl) {

        List<ClientLinkDto> list = customClientLinkRepositoryImp.findClientLinkUsingFindAndModify(cl);
               // .orElseThrow(() -> new UserNotFoundException("Client Link List not found"));

        List<ClientLinkDto> result = list.stream().map(clientLink -> ClientLinkDto.builder()
                        .clientName(clientLink.getClientName())
                        .linkName(clientLink.getLinkName())
                        .statement(clientLink.getStatement())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    public ClientLinkDto updateClientLinkUsingFindAndModify(Client cl, Link link, Boolean st) {

        ClientLink clientLink =customClientLinkRepositoryImp.updateClientLinkUsingFindAndModify(cl, link, st);

        logger.info("{} activated {} :: Execution Time - {}", cl.getName(), link.getName(), dateTimeFormatter.format(LocalDateTime.now()));

        ClientLinkDto dto = ClientLinkDto.builder()
                .clientName(clientLink.getClientId())
                .linkName(clientLink.getLinkId())
                .statement(clientLink.getStatement())
                .build();

        return dto;
    }
}
