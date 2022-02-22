package com.example.LoanApplicationSystem.controller;

import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping ("/api/customer")
@CrossOrigin()
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/addCustomer")
    public void add(@RequestBody Customer customer) {

        customerService.addCustomer(customer);
    }

    @GetMapping(value="/{id}")
    public Customer getCustomerById(@PathVariable @Min(1) Integer id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("/deleteCustomer")
    public boolean deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/getAllCustomer")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

}
