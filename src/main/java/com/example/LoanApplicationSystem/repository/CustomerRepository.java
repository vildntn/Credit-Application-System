package com.example.LoanApplicationSystem.repository;

import com.example.LoanApplicationSystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
