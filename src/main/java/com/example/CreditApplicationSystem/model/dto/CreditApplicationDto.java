package com.example.CreditApplicationSystem.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreditApplicationDto {

    @NotBlank
    private String creditStatus;
    @NotBlank
    private int creditLimit;

}
