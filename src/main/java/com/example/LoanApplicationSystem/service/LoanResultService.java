package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.model.entity.LoanResult;

import java.util.List;

public interface LoanResultService {
    void addLoanResult(LoanResult loanResult);

    boolean deleteLoanResult(int id);

    LoanResult updateLoanResult(LoanResult loanResult);

    LoanResult getLoanResultById(int id);

    List<LoanResult> getAllLoanResult();

    String checkLoanResultByLoanApplication(int id);
}
