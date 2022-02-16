package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.LoanResult;

import java.util.List;

public interface LoanResultService {
    String addLoanResult(LoanResult loanResult);

    boolean deleteLoanResult(int id);

    LoanResult updateLoanResult(LoanResult loanResult);

    LoanResult getLoanResultById(int id);

    List<LoanResult> getAllLoanResult();
}
