package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Integer> {
}
