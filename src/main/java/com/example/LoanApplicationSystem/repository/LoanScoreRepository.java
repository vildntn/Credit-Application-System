package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.LoanScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanScoreRepository extends JpaRepository<LoanScore,Integer> {
    int getLoanScoreByCustomerId(int id);
}
