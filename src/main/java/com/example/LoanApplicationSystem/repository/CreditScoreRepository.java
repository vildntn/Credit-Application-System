package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditScoreRepository extends JpaRepository<CreditScore,Integer> {
    int getCreditScoreByCustomerId(int id);
}
