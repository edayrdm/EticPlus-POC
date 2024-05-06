package com.shopny.EticPlus.Entities;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClientLinkDto {

    private String clientName;
    private String linkName;
    private Boolean statement;
}
