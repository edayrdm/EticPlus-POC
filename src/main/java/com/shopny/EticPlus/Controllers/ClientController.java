package com.shopny.EticPlus.Controllers;

import com.shopny.EticPlus.DTOs.ClientDto;
import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Services.ClientLinkService;
import com.shopny.EticPlus.Services.ClientService;
import com.shopny.EticPlus.Services.LinkService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final LinkService linkService;
    private final ClientLinkService clientLinkService;

    @Autowired
    public ClientController(ClientService cityBulkWriteService, LinkService linkService, ClientLinkService clientLinkService) {
        this.clientService = cityBulkWriteService;
        this.linkService = linkService;
        this.clientLinkService = clientLinkService;
    }

    @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createClientData(@RequestBody @Valid ClientDto clientData){

        Client newData = clientService.createClient(clientData);
        DefaultSignUpForLinks(newData);
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
        return ResponseEntity.ok().body(clientService.getClientByIdAndPassword(name, psw));
    }

    @GetMapping(path="/links/{clientId}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientLinkDto>> getAllClientLink(@PathVariable String clientId) {

        Client client = clientService.getClientById(clientId);
        List<ClientLinkDto> dataList = clientLinkService.getClientLinkByClient(client);
        return new ResponseEntity<List<ClientLinkDto>>(dataList,HttpStatus.OK);
    }

    @PostMapping(path="/updateLink",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientLinkDto> updateClientLink(@RequestParam(name = "clientId") String clientId,
                                                          @RequestParam(name = "linkId") String linkId,
                                                          @RequestParam(name = "statement") Boolean st){

        Client client = clientService.getClientById(clientId);
        Link link = linkService.getLinkById(linkId);
        ClientLinkDto newData = clientLinkService.updateClientLinkUsingFindAndModify(client, link, st);

        return new ResponseEntity<ClientLinkDto>(newData, HttpStatus.CREATED);
    }

    @PostMapping(path = "/delete",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteClient(@RequestParam(name = "name", required = true) String name,
                                            @RequestParam(name = "password", required = true) String psw) {

        Client client = clientService.getClientByIdAndPassword(name, psw);
        clientService.deleteClient(client.getId());
        clientLinkService.deleteClientLinkById(client.getId());

        return ResponseEntity.ok().body("Client deleted successfully");
    }

    public void DefaultSignUpForLinks(Client cl){

        Link link;
        ClientLink clink;

        link = linkService.getLinkByName("Günlük Satış Raporu");
        clink =  new ClientLink(cl.getId(), link.getId(), false);
        clientLinkService.createClientLink(clink);

        link = linkService.getLinkByName("Google Analytics");
        clink =  new ClientLink(cl.getId(), link.getId(), false);
        clientLinkService.createClientLink(clink);

        link = linkService.getLinkByName("Chatmate");
        clink =  new ClientLink(cl.getId(), link.getId(), false);
        clientLinkService.createClientLink(clink);
    }

}
