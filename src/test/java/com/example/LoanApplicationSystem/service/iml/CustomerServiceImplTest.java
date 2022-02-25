package com.example.LoanApplicationSystem.service.iml;

import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.repository.CustomerRepository;
import com.example.CreditApplicationSystem.service.iml.CreditScoreServiceImpl;
import com.example.CreditApplicationSystem.service.iml.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CreditScoreServiceImpl creditScoreService;

    @Test
    void addCustomer() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);

        //stub - when
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        //then
        customerService.addCustomer(expectedCustomer);

        verify(customerRepository, times(1)).save(expectedCustomer);

    }

    @Test
    void updateCustomer() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer newCustomer = expectedCustomer;
        newCustomer.setNationalID("81946338254");
        newCustomer.setFirstName("Adrian");
        //stub - when
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        //then
        Customer actualCustomer = customerService.updateCustomer(newCustomer);

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void deleteCustomer() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);

        //stub - when
        when(customerRepository.findById(1)).thenReturn(Optional.of(expectedCustomer));
        boolean deletedCustomer = customerService.deleteCustomer(1);
        assertTrue(deletedCustomer);

    }

    @Test
    void getAllCustomer() {
        //init step
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer1 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer);

        //stub-when
        when(customerRepository.findAll()).thenReturn(customers);

        //then
        List<Customer> allCustomer = customerService.getAllCustomer();
        assertEquals(customers.size(), allCustomer.size());

    }

    @Test
    void getAllCustomerBySortedDesc() {
    }

    @Test
    void getCustomerById_successful() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);

        //stub - when
        when(customerRepository.findById(1)).thenReturn(Optional.of(expectedCustomer));

        //then step
        Customer actualCustomer = customerService.getCustomerById(1);

        //valid step
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void getCustomerById_not_found() {
        //stub - when
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        //then step
        assertThrows(NotFoundException.class, () -> {
            Customer actualCustomer = customerService.getCustomerById(1);
        });

    }


    @Test
    void getCustomerByNationalID_success() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);

        //stub - when
        when(customerRepository.findByNationalID("81566338254")).thenReturn(Optional.of(expectedCustomer));

        //then step
        Customer actualCustomer = customerService.getCustomerByNationalID("81566338254");

        //valid step
        assertEquals(expectedCustomer, actualCustomer);

    }

    @Test
    void getCustomerByNationalID_not_found() {

        //stub - when
        when(customerRepository.findByNationalID("81566338254")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            //then step
            Customer actualCustomer = customerService.getCustomerByNationalID("81566338254");
        });
    }

    @Test
    void checkIfCustomerAlreadyExist_not_exist() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);

        //stub - when

        //then step
        boolean actualCustomer = customerService.checkIfCustomerAlreadyExist("81566338254");

        //Valid step
        assertFalse(actualCustomer);
    }

    @Test
    void checkIfCustomerAlreadyExist_exist() {
        //init step
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2=new Customer(2,"Andrew", "Hooper","22255942075","2255942075",4500);
        List<Customer> customers=new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        //stub - when

         when(customerService.getCustomerById(1)).thenReturn(customer1);
        //then step
        boolean actualCustomer = customerService.checkIfCustomerAlreadyExist("81566338254");

        //Valid step
        assertTrue(actualCustomer);
    }
}