package com.shopny.EticPlus.Services;


import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSignUpSettings {

    private final LinkService linkService;
    private final ClientLinkService clientLinkService;

    @Autowired
    public DefaultSignUpSettings(LinkService linkService, ClientLinkService clientLinkService) {
        this.linkService = linkService;
        this.clientLinkService = clientLinkService;
    }

    public void DefaultSignUpForLinks(Client client){

        List<Link> linkList = linkService.getAllLinkData();

        linkList.forEach(link -> {
                ClientLink clink;
                clink =  new ClientLink(client.getId(), link.getId(), link.getStatus());
                clientLinkService.createClientLink(clink);
            }
        );
    }
}
