package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.Loan;

import java.util.List;

public interface LoanService {

    void addLoan(Loan loan);
    boolean deleteLoan (int id);
    Loan updateLoan (Loan loan);
    List<Loan> getAllLoan();
    Loan getLoanByCustomerId(int id);
    Loan getLoanById(int id);

    int getLoanLimitByCustomerId(int id);
}
