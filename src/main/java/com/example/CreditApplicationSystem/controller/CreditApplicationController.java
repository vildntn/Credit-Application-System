package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.model.dto.CreditApplicationDto;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.model.mapper.CreditApplicationMapper;
import com.example.CreditApplicationSystem.service.CreditApplicationService;
import com.example.CreditApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/creditApplication")
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
