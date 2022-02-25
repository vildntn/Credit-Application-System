package com.example.CreditApplicationSystem.service;

import com.example.CreditApplicationSystem.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    
    List<Customer> getAllCustomer();
    Customer getCustomerById(int id);
    Customer getCustomerByNationalID(String nationalID);
    boolean checkIfCustomerAlreadyExist(String nationalID);
}
