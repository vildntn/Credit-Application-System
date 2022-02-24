package com.example.LoanApplicationSystem.controller;

import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getCustomerByNationalId")
    public Customer getCustomerByNationalID( @RequestParam(required=false,name="nationalID") @Valid String nationalID){
        return customerService.getCustomerByNationalID(nationalID);
    }

//    @GetMapping("/findByNationalID")
//    public Optional<Customer> findByNationalId( @RequestParam(required=false,name="nationalID") String nationalId){
//        return customerService.findByNationalId(nationalId);
//    }

}
