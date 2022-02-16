package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Loan getLoanByCustomerId(int id);
}
