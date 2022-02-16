package com.example.LoanApplicationSystem.service;

import com.example.LoanApplicationSystem.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(int id);
    List<Customer> getAllCustomer();
    List<Customer> getAllCustomerBySortedDesc();
    Customer getCustomerById(int id);

    int getMonthlyIncomeByCustomerId(int id);
}
