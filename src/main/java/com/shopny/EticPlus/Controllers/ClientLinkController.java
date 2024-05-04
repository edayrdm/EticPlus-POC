package com.shopny.EticPlus.Controllers;


import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Services.ClientLinkService;
import com.shopny.EticPlus.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/clientLink")
public class ClientLinkController {

    private final ClientLinkService clientLinkService;
    private final ClientService clientService;

    @Autowired
    public ClientLinkController(ClientService clientService, ClientLinkService clientLinkService ) {
        this.clientService = clientService;
        this.clientLinkService = clientLinkService;
    }



}
