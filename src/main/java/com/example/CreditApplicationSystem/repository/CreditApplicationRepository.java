package com.example.CreditApplicationSystem.repository;

import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Integer> {
    CreditApplication findByCustomer_NationalID(String nationalID);
}
