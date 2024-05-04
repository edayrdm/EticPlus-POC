package com.shopny.EticPlus.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "ClientLink")
public class ClientLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String clientId;
    private String linkId;
    private Boolean statement;

    public ClientLink(String cl, String link, Boolean st) {
        this.clientId = cl;
        this.linkId = link;
        this.statement = st;
    }
}
