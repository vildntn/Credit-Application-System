package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditScore")
public class CreditScoreController {

    @Autowired
    private CreditScoreService creditScoreService;

    @PostMapping("/addCreditScore")
    public void addCreditScore(@RequestBody CreditScore creditScore){
        creditScoreService.addCreditScore(creditScore);
    }

    @PostMapping("/deleteCreditScore")
    public boolean deleteCreditScore(@RequestParam int id){
       return creditScoreService.deleteCreditScore(id);
    }

    @GetMapping("/getCreditScoreById/{id}")
   public CreditScore getCreditScoreById(int id){
        return creditScoreService.getCreditScoreById(id);
    }

    @GetMapping("/getAllCreditScore")
   public List<CreditScore> getAllCreditScore(){
       return creditScoreService.getAllCreditScore();
    }

    @GetMapping("/getCreditScoreByNationalID/{nationalID}")
    public CreditScore getCreditScoreByCustomerNationalID(String nationalID){
        return creditScoreService.getCreditScoreByCustomerNationalID(nationalID);
    }

}
