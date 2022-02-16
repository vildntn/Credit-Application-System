package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer> {
}
