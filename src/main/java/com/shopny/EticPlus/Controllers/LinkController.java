package com.shopny.EticPlus.Controllers;

import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Services.ClientLinkService;
import com.shopny.EticPlus.Services.ClientService;
import com.shopny.EticPlus.Services.DefaultSignUpSettings;
import com.shopny.EticPlus.Services.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/link")
public class LinkController {

    private final ClientService clientService;
    private final LinkService linkService;
    private final ClientLinkService clientLinkService;

    @Autowired
    public LinkController(ClientService clientService,
                            LinkService linkService,
                            ClientLinkService clientLinkService) {
        this.clientService = clientService;
        this.linkService = linkService;
        this.clientLinkService = clientLinkService;
    }

    @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Link> createLinkData(@RequestBody @Valid Link linkData){

        Link newData = linkService.createLink(linkData);
        List<Client> clientList = clientService.getAllClientData();

        List<ClientLink> result = clientList.stream().map(clientLink -> ClientLink.builder()
                        .clientId(clientLink.getId())
                        .linkId(newData.getId())
                        .statement(newData.getStatus())
                        .build())
                .toList();

        clientLinkService.createClientLinkAll(result);

        return new ResponseEntity<Link>(newData, HttpStatus.CREATED);
    }
}
