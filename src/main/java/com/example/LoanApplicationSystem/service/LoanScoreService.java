package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.LoanScore;

import java.util.List;

public interface LoanScoreService {

    void addLoanScore(LoanScore loanScore);
    boolean deleteLoanScore(int id);
    LoanScore getLoanScoreById(int id);
    List<LoanScore> getAllLoanScore();
    int getLoanScoreByCustomerId(int id);
    void createLoanScoreToCustomer(Customer customer);
    LoanScore getLoanScoreByCustomerIdentNumber(String identificationNumber);

}
