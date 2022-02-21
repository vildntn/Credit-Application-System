package com.example.LoanApplicationSystem.controller;


import com.example.LoanApplicationSystem.model.entity.LoanResult;
import com.example.LoanApplicationSystem.service.LoanResultService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanResult")
public class LoanResultController {

    @Autowired
    private LoanResultService loanResultService;


    @GetMapping("/getAllLoanResult")
    public List<LoanResult> getAllLoanApplication(){
        return loanResultService.getAllLoanResult();
    }

    @GetMapping("/getLoanResultById")
    public LoanResult getLoanResultById(int id){
        return loanResultService.getLoanResultById(id);
    }

    @PostMapping("/addLoanResult")
    public void addLoanResult( @RequestBody LoanResult loanResult){
        loanResultService.addLoanResult(loanResult);
    }

    @GetMapping("/checkLoanResultByLoanApplication")
    public String checkLoanResultByLoanApplication(int id){
        return loanResultService.checkLoanResultByLoanApplication(id);
    }
}
