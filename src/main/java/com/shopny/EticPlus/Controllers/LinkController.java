package com.shopny.EticPlus.Controllers;

import com.shopny.EticPlus.Entities.Link;
import com.shopny.EticPlus.Services.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    //Create new Rate Data
    @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Link> createLinkData(@RequestBody @Valid Link linkData){

        Link newData = linkService.createLink(linkData);
        return new ResponseEntity<Link>(newData, HttpStatus.CREATED);
    }

}
