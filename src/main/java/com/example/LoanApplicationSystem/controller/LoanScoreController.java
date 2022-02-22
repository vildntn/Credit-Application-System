package com.example.LoanApplicationSystem.controller;

import com.example.LoanApplicationSystem.model.entity.LoanScore;
import com.example.LoanApplicationSystem.service.LoanScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanScore")
public class LoanScoreController {

    @Autowired
    private LoanScoreService loanScoreService;

    @PostMapping("/addLoanScore")
    public void addLoanScore(@RequestBody LoanScore loanScore){
        loanScoreService.addLoanScore(loanScore);
    }

    @PostMapping("/deleteLoanScore")
    public boolean deleteLoanScore(@RequestParam int id){
       return loanScoreService.deleteLoanScore(id);
    }

    @GetMapping("/getLoanScoreById/{id}")
   public LoanScore getLoanScoreById(int id){
        return loanScoreService.getLoanScoreById(id);
    }

    @GetMapping("/getAllLoanScore")
   public List<LoanScore> getAllLoanScore(){
       return loanScoreService.getAllLoanScore();
    }

    @GetMapping("/getLoanScoreByIdentificationNumber/{identificationNumber}")
    public LoanScore getLoanScoreByCustomerIdentNumber(String identificationNumber){
        return loanScoreService.getLoanScoreByCustomerIdentNumber(identificationNumber);
    }

}
