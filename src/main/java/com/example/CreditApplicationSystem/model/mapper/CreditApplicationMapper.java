package com.example.CreditApplicationSystem.model.mapper;


import com.example.CreditApplicationSystem.model.dto.CreditApplicationDto;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;


public class CreditApplicationMapper {

    public static CreditApplicationDto toDto(CreditApplication creditApplication){
        CreditApplicationDto creditApplicationDto =new CreditApplicationDto();
        creditApplicationDto.setCreditLimit(creditApplication.getCreditLimit());
        creditApplicationDto.setCreditStatus(creditApplication.getCreditStatus());
        return creditApplicationDto;
    }
}
