package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.LoanApplication;

import java.util.List;

public interface LoanApplicationService {

     String addLoanApplication(LoanApplication loanApplication);

     String deleteLoanApplication(int id);

     LoanApplication updateLoanApplication(LoanApplication loanApplication);

     LoanApplication getLoanApplicationById(int id);

     List<LoanApplication> getAllLoanApplication();

}
