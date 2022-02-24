package com.example.LoanApplicationSystem.model.mapper;


import com.example.LoanApplicationSystem.model.dto.CreditApplicationDto;
import com.example.LoanApplicationSystem.model.entity.CreditApplication;


public class CreditApplicationMapper {

    public static CreditApplicationDto toDto(CreditApplication creditApplication){
        CreditApplicationDto creditApplicationDto =new CreditApplicationDto();
        creditApplicationDto.setCreditLimit(creditApplication.getCreditLimit());
        creditApplicationDto.setCreditStatus(creditApplication.getCreditStatus());
        return creditApplicationDto;
    }
}
