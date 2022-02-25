package com.example.CreditApplicationSystem.repository;

import com.example.CreditApplicationSystem.model.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditScoreRepository extends JpaRepository<CreditScore,Integer> {
    int getCreditScoreByCustomerId(int id);
}
