package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.LoanResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanResultRepository extends JpaRepository<LoanResult,Integer> {
}
