package com.example.LoanApplicationSystem.controller;


import com.example.LoanApplicationSystem.model.dto.CreditApplicationDto;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.CreditApplication;
import com.example.LoanApplicationSystem.model.mapper.CreditApplicationMapper;
import com.example.LoanApplicationSystem.service.CreditApplicationService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/creditApplication")
@CrossOrigin()
public class CreditApplicationController {

    @Autowired
    private CreditApplicationService creditApplicationService;


    @GetMapping("/getAllCreditApplication")
    public List<CreditApplication> getAllLoanApplication(){
        return creditApplicationService.getAllCreditApplication();
    }

    @GetMapping("/getCreditApplicationById")
    public CreditApplication getLoanApplicationById(int id){
        return creditApplicationService.getCreditApplicationById(id);
    }



    @PostMapping("/addCreditApplication")
    public String addLoanApplication( @RequestBody CreditApplication loanApplication){
        creditApplicationService.addCreditApplication(loanApplication);
        return Messages.creditApplicationAdded;
    }
    @GetMapping("/checkCreditApplicationStatus")
    public CreditApplication checkLoanApplicationStatus(String identificationNumber){
        return creditApplicationService.checkCreditApplicationStatus(identificationNumber);
    }

    @GetMapping("/getCreditApplicationByCustomerId")
    public CreditApplication getLoanApplicationByCustomerId(@RequestParam @Min(1) int  id){
        return creditApplicationService.getCreditApplicationByCustomerId(id);
    }

    @PostMapping("/checkCreditApplicationResult")
    public CreditApplicationDto checkLoanApplicationResult(@RequestBody @Valid Customer customer){
        return CreditApplicationMapper.toDto(creditApplicationService.checkCreditApplicationResult(customer));
    }

}

