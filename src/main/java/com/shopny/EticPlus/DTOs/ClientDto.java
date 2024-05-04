package com.shopny.EticPlus.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClientDto {

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    String name;

    @NotBlank(message = "Invalid Explanation: Empty exp")
    @NotNull (message = "Invalid Explanation: EXP is NULL")
    String details;

    @NotBlank(message = "Invalid Password: Empty password")
    @NotNull (message = "Invalid Password: Password is NULL")
    String password;

    @NotBlank(message = "Invalid Account: Empty account")
    @NotNull (message = "Invalid Account: Account is NULL")
    String account;
}
