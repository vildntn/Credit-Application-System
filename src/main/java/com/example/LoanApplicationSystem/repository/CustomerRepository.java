package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
Optional<Customer> findByNationalID(String nationalID);
}
