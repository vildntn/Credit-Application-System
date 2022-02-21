package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.LoanScore;

import java.util.List;

public interface LoanScoreService {

    void addLoanScore(LoanScore loanScore);
    boolean deleteLoanScore(int id);
    LoanScore updateLoanScore(LoanScore loanScore);
    LoanScore getLoanScoreById(int id);
    List<LoanScore> getAllLoanScore();
    int getLoanScoreByCustomerId(int id);

    LoanScore getLoanScoreByCustomerIdentNumber(String identificationNumber);

}
