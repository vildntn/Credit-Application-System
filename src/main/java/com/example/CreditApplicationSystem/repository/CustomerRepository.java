package com.example.CreditApplicationSystem.repository;

import com.example.CreditApplicationSystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
Optional<Customer> findByNationalID(String nationalID);
}
