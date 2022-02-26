package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping ("/api/customer")
@CrossOrigin()
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/create")
    public void add(@RequestBody Customer customer) {

        customerService.addCustomer(customer);
    }

    @GetMapping(value="/{id}")
    public Customer getCustomerById(@PathVariable @Min(1) Integer id){
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/delete")
    public boolean deleteCustomer(@RequestParam @Min(1) Integer id){
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/getCustomerByNationalID")
    public Customer getCustomerByNationalID( @RequestParam(required=false,name="nationalID") @Valid String nationalID){
        return customerService.getCustomerByNationalID(nationalID);
    }

}
