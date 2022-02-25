package com.example.CreditApplicationSystem.service;

import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;

import java.util.List;

public interface CreditApplicationService {

     void addCreditApplication(CreditApplication creditApplication);

     boolean deleteCreditApplication(int id);

     CreditApplication updateCreditApplication(CreditApplication creditApplication);

     CreditApplication getCreditApplicationById(int id);

     List<CreditApplication> getAllCreditApplication();

     CreditApplication getCreditApplicationByCustomerId(int id);

     CreditApplication checkCreditApplicationStatus(String nationalID);

     CreditApplication checkCreditApplicationResult(Customer customer);


}
