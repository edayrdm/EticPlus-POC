package com.shopny.EticPlus.Controllers;

import com.shopny.EticPlus.DTOs.ClientDto;
import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Services.ClientLinkService;
import com.shopny.EticPlus.Services.ClientService;
import com.shopny.EticPlus.Services.DefaultSignUpSettings;
import com.shopny.EticPlus.Services.LinkService;
import com.shopny.EticPlus.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final LinkService linkService;
    private final ClientLinkService clientLinkService;
    private final DefaultSignUpSettings defaultSignUpSettings;


    @Autowired
    public ClientController(ClientService clientService,
                            LinkService linkService,
                            ClientLinkService clientLinkService,
                            DefaultSignUpSettings defaultSignUpSettings) {
        this.clientService = clientService;
        this.linkService = linkService;
        this.clientLinkService = clientLinkService;
        this.defaultSignUpSettings = defaultSignUpSettings;
    }

    @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createClientData(@RequestBody @Valid ClientDto clientData){

        Client newData = clientService.createClient(clientData);
        defaultSignUpSettings.DefaultSignUpForLinks(newData);
        return new ResponseEntity<Client>(newData, HttpStatus.CREATED);
    }

    @GetMapping(path="/all", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getAllClientData() {

        List<Client> dataList = clientService.getAllClientData();
        return new ResponseEntity<List<Client>>(dataList,HttpStatus.OK);
    }

    @GetMapping(path = "/login")
    public ResponseEntity<Client> loginClient(@RequestParam(name = "name", required = true) String name,
                                            @RequestParam(name = "password", required = true) String psw) {
        return ResponseEntity.ok().body(clientService.getClientByNameAndPassword(name, psw));
    }

    @GetMapping(path="/links/{clientId}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientLinkDto>> getAllClientLink(@PathVariable String clientId) {

        Client client = clientService.getClientById(clientId);
        List<ClientLinkDto> dataList = clientLinkService.getClientLinkByClient(client);

        return new ResponseEntity<List<ClientLinkDto>>(dataList,HttpStatus.OK);
    }

    @PostMapping(path="/activateLink",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientLinkDto> activateClientLink(@RequestParam(name = "clientId") String clientId,
                                                            @RequestParam(name = "linkId") String linkId){

        Client client = clientService.getClientById(clientId);
        clientLinkService.permissionForLink(client);

        Link link = linkService.getLinkById(linkId);
        ClientLinkDto newData = clientLinkService.updateClientLinkStatus(client, link, true);

        return new ResponseEntity<ClientLinkDto>(newData, HttpStatus.CREATED);
    }

    @PostMapping(path="/deactivateLink",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientLinkDto> deactivateClientLink(@RequestParam(name = "clientId") String clientId,
                                                            @RequestParam(name = "linkId") String linkId){

        Client client = clientService.getClientById(clientId);
        Link link = linkService.getLinkById(linkId);
        ClientLinkDto newData = clientLinkService.updateClientLinkStatus(client, link, false);

        return new ResponseEntity<ClientLinkDto>(newData, HttpStatus.CREATED);
    }

    @PostMapping(path = "/delete",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteClient(@RequestParam(name = "name", required = true) String name,
                                               @RequestParam(name = "password", required = true) String psw) {

        Client client = clientService.getClientByNameAndPassword(name, psw);
        clientService.deleteClient(client.getId());
        clientLinkService.deleteClientLinkById(client.getId());

        return ResponseEntity.ok().body("Client deleted successfully");
    }

}
