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
@CrossOrigin()
public class CreditApplicationController {
    @Autowired
    private CreditApplicationService creditApplicationService;


    @GetMapping("/all")
    public List<CreditApplication> getAllCreditApplication(){
        return creditApplicationService.getAllCreditApplication();
    }

    @GetMapping("/{id}")
    public CreditApplication getCreditApplicationById(@PathVariable @Min(1) int id){
        return creditApplicationService.getCreditApplicationById(id);
    }



    @PostMapping("/create")
    public void addCreditApplication( @RequestBody CreditApplication loanApplication){
        creditApplicationService.addCreditApplication(loanApplication);
    }
    @GetMapping("/checkCreditApplicationStatus")
    public CreditApplication checkCreditApplicationStatus(@RequestParam(required=false,name="nationalID") @Valid String nationalID){
        return creditApplicationService.checkCreditApplicationStatus(nationalID);
    }

    @GetMapping("/by-customer")
    public CreditApplication getCreditApplicationByCustomerId(@RequestParam @Min(1) int  id){
        return creditApplicationService.getCreditApplicationByCustomerId(id);
    }

    @PostMapping("/checkCreditApplicationResult")
    public CreditApplicationDto checkCreditApplicationResult(@RequestBody @Valid Customer customer){
        return CreditApplicationMapper.toDto(creditApplicationService.checkCreditApplicationResult(customer));
    }
}
