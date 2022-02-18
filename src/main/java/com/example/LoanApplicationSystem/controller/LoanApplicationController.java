package com.example.LoanApplicationSystem.controller;

import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.service.LoanApplicationService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanApplication")
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


}
