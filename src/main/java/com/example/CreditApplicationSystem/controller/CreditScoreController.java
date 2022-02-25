package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/creditScore")
public class CreditScoreController {

    @Autowired
    private CreditScoreService creditScoreService;

    @PostMapping("/create")
    public void addCreditScore(@RequestBody CreditScore creditScore){
        creditScoreService.addCreditScore(creditScore);
    }

    @PostMapping("/delete")
    public boolean deleteCreditScore(@RequestParam int id){
       return creditScoreService.deleteCreditScore(id);
    }

    @GetMapping("/{id}")
   public CreditScore getCreditScoreById(@PathVariable @Min(1) int id){
        return creditScoreService.getCreditScoreById(id);
    }

    @GetMapping("/all")
   public List<CreditScore> getAllCreditScore(){
       return creditScoreService.getAllCreditScore();
    }

    @GetMapping("/getCreditScoreByNationalID")
    public CreditScore getCreditScoreByCustomerNationalID( @RequestParam(required=false,name="nationalID") @Valid String nationalID){
        return creditScoreService.getCreditScoreByCustomerNationalID(nationalID);
    }

}
