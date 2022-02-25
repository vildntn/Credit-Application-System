package com.example.CreditApplicationSystem.service.iml;

import com.example.CreditApplicationSystem.repository.CustomerRepository;
import com.example.CreditApplicationSystem.service.constant.Messages;
import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.CustomerService;
import com.example.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditScoreService creditScoreService;

    @Override
    public void addCustomer(Customer customer) {
        if(!checkIfCustomerAlreadyExist(customer.getNationalID())){
            customerRepository.save(customer);
            creditScoreService.createCreditScoreToCustomer(customer);
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
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
    public Customer getCustomerByNationalID(String nationalID) {
        List<Customer> allCustomer = getAllCustomer();
        return allCustomer.stream()
                .filter((customer)->customer.getNationalID().equals(nationalID))
                .findFirst().orElseThrow(()->new NotFoundException(Messages.customerNotFound));
    }


    @Override
    public boolean checkIfCustomerAlreadyExist(String nationalID){
        //eğer customer varsa false falan dönsğn ama acaba customer yerine tckn mu alsam
        //ya da optional class ile mi sağlsam bu durumu
        List<Customer> allCustomer = getAllCustomer();
        return allCustomer.stream().anyMatch((c) -> c.getNationalID().equals(nationalID));
    }

//    @Override
//    public Optional<Customer> findByNationalId(String nationalId) {
//        return customerRepository.findByNationalID(nationalId);
//    }
}
