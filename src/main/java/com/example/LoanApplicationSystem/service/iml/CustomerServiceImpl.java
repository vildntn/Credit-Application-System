package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.repository.CustomerRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(int id) {
        customerRepository.delete(getCustomerById(id));
        return true;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomerBySortedDesc() {
        return null;
    }

    @Override
    public Customer getCustomerById(int id) {
        Optional<Customer> customerById =customerRepository.findById(id);
        return customerById.orElseThrow(()->new NotFoundException(Messages.customerNotFound));
    }

    @Override
    public Customer getCustomerByIdentificationNumber(String identificationNumber) {
        List<Customer> allCustomer = getAllCustomer();
        return allCustomer.stream()
                .filter((customer)->customer.getIdentificationNumber().equals(identificationNumber))
                .findFirst().orElseThrow(()->new NotFoundException(Messages.customerNotFound));
    }

    @Override
    public int getMonthlyIncomeByCustomerId(int id) {
        Customer customerById = getCustomerById(id);
        return customerById.getMonthlyIncome() ;
    }
}
