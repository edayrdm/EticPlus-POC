package com.shopny.EticPlus.Entities;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Link")
public class Link {

    @Id
    private String id;
    @NotNull
    private String name;
}
