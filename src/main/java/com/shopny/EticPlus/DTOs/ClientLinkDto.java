package com.shopny.EticPlus.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
@Builder
public class ClientLinkDto {

    private String clientName;
    private String linkName;
    private Boolean statement;
}
