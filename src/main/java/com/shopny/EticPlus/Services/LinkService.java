package com.shopny.EticPlus.Services;

import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Repositories.LinkRepository;
import com.shopny.EticPlus.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(Link link) {
        return linkRepository.save(link);
    }

    public List<Link> getAllLinkData() {
        return linkRepository.findAll();
    }

    public Link getLinkByName(String name) {
        return linkRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("Link name is not correct"));
    }

    public Link getLinkById(String id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Link id is not correct"));
    }
}
