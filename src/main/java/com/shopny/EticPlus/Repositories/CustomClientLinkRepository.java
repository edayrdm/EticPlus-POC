package com.shopny.EticPlus.Repositories;

import com.shopny.EticPlus.Entities.Client;
import com.shopny.EticPlus.Entities.ClientLink;
import com.shopny.EticPlus.Entities.ClientLinkDto;
import com.shopny.EticPlus.Entities.Link;

import java.util.List;


public interface CustomClientLinkRepository {

    ClientLink updateClientLinkUsingFindAndModify(Client cl, Link l, Boolean st);

    List<ClientLinkDto> findClientLinkUsingFindAndModify(Client cl);
}
