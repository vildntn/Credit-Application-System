package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.LoanScore;

public interface LoanScoreService {

    void addLoanScore(LoanScore loanScore);
    boolean deleteLoanScore(int id);
    LoanScore updateLoanScore(LoanScore loanScore);
    LoanScore getLoanScoreById(int id);

    int getLoanScoreByCustomerId(int id);

}
