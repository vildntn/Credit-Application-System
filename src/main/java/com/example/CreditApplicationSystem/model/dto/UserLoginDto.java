package com.example.CreditApplicationSystem.model.dto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}