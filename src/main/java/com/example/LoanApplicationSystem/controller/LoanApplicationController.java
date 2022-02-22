package com.example.LoanApplicationSystem.controller;

import com.example.LoanApplicationSystem.model.dto.LoanApplicationDto;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.model.mapper.LoanApplicationMapper;
import com.example.LoanApplicationSystem.service.LoanApplicationService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/loanApplication")
@CrossOrigin()
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;


    @GetMapping("/getAllLoanApplication")
    public List<LoanApplication> getAllLoanApplication(){
        return loanApplicationService.getAllLoanApplication();
    }

    @GetMapping("/getLoanApplicationById")
    public LoanApplication getLoanApplicationById(int id){
        return loanApplicationService.getLoanApplicationById(id);
    }



    @PostMapping("/addLoanApplication")
    public String addLoanApplication( @RequestBody LoanApplication loanApplication){
        loanApplicationService.addLoanApplication(loanApplication);
        return Messages.loanApplicationAdded;
    }
    @GetMapping("/checkLoanApplicationStatus")
    public LoanApplication checkLoanApplicationStatus(String identificationNumber){
        return loanApplicationService.checkLoanApplicationStatus(identificationNumber);
    }

    @GetMapping("/getLoanApplicationByCustomerId")
    public LoanApplication getLoanApplicationByCustomerId(@RequestParam @Min(1) int  id){
        return loanApplicationService.getLoanApplicationByCustomerId(id);
    }

    @PostMapping("/checkLoanApplicationResult")
    public LoanApplicationDto checkLoanApplicationResult(@RequestBody @Valid Customer customer){
        return LoanApplicationMapper.toDto(loanApplicationService.checkLoanApplicationResult(customer));
    }

}
