package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    List<Customer> getAllCustomer();
    List<Customer> getAllCustomerBySortedDesc();
    Customer getCustomerById(int id);
    Customer getCustomerByNationalID(String nationalID);
    boolean checkIfCustomerAlreadyExist(String nationalID);
//    Optional<Customer> findByNationalId(String nationalId);
}
