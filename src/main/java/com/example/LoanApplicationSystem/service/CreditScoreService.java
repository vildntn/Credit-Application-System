package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.CreditScore;
import com.example.LoanApplicationSystem.model.entity.Customer;

import java.util.List;

public interface CreditScoreService {

    void addCreditScore(CreditScore creditScore);
    boolean deleteCreditScore(int id);
    CreditScore getCreditScoreById(int id);
    List<CreditScore> getAllCreditScore();
    int getCreditScoreByCustomerId(int id);
    void createCreditScoreToCustomer(Customer customer);
    CreditScore getCreditScoreByCustomerNationalID(String nationalID);

}
