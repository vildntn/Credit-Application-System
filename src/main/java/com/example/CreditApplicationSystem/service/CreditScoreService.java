package com.example.CreditApplicationSystem.service;

import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;

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
