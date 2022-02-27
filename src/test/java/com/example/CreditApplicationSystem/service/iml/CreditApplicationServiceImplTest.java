package com.example.CreditApplicationSystem.service.iml;


import com.example.CreditApplicationSystem.exception.AlreadyExistException;
import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceImplTest {

    @Mock
    private CreditApplicationRepository creditApplicationRepository;

    @InjectMocks
    private CreditApplicationServiceImpl creditApplicationService;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private CreditScoreServiceImpl creditScoreService;



    @Test
    void addCreditApplication() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());

        //stub - when
        when(creditApplicationRepository.save(creditApplication)).thenReturn(creditApplication);

        //then step
       creditApplicationService.addCreditApplication(creditApplication);
        verify(creditApplicationRepository, times(1)).save(creditApplication);
    }

    @Test
    void deleteCreditApplication() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        //stub - when
        when(creditApplicationRepository.findById(1)).thenReturn(Optional.of(creditApplication));
        
        //then step
        boolean deletedCreditApplication = creditApplicationService.deleteCreditApplication(1);
        assertTrue(deletedCreditApplication);
    }

    @Test
    void deleteCreditApplication_not() {
        //stub - when
        when(creditApplicationRepository.findById(1)).thenReturn(Optional.empty());

        //then step
        assertThrows(AlreadyExistException.class, () -> {
            boolean deletedCreditApplication = creditApplicationService.deleteCreditApplication(1);
        });

    }

    @Test
    void updateCreditApplication() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        creditApplication.setCreditStatus("Rejected");

        //stub- when
        when(creditApplicationRepository.save(creditApplication)).thenReturn(creditApplication);
        //then step
        CreditApplication actualCreditApp = creditApplicationService.updateCreditApplication(creditApplication);
        assertEquals(creditApplication,actualCreditApp);
    }

    @Test
    void getCreditApplicationById_successful() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        //stub- when
        when(creditApplicationRepository.findById(1)).thenReturn(Optional.of(creditApplication));

        //then
        CreditApplication actualCreditApplication = creditApplicationService.getCreditApplicationById(1);
        assertEquals(creditApplication,actualCreditApplication);

    }

    @Test
    void getCreditApplicationById_not_found() {
        //stub- when
        when(creditApplicationRepository.findById(1)).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> {
            CreditApplication actualCreditApplication = creditApplicationService.getCreditApplicationById(1);
        });
    }

    @Test
    void getAllCreditApplication_successful() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer1 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        CreditApplication creditApplication1=new CreditApplication(1,customer1,"Rejected",0,new Date());
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication1);
        creditApplications.add(creditApplication);
        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);
        //then
        List<CreditApplication> allCreditApplication = creditApplicationService.getAllCreditApplication();
        assertEquals(creditApplications.size(),allCreditApplication.size());

    }
    @Test
    void getAllCreditApplication_empty() {

        List<CreditApplication> creditApplications=new ArrayList<>();
        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);
        //then
        List<CreditApplication> allCreditApplication = creditApplicationService.getAllCreditApplication();
        assertEquals(0,allCreditApplication.size());

    }

    @Test
    void getCreditApplicationByCustomerId_successful() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer1 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        CreditApplication creditApplication1=new CreditApplication(1,customer1,"Rejected",0,new Date());
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication1);
        creditApplications.add(creditApplication);
        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);

        //then
        CreditApplication actualCreditApplication = creditApplicationService.getCreditApplicationByCustomerId(1);
        assertEquals(creditApplication,actualCreditApplication);
    }

    @Test
    void getCreditApplicationByCustomerId_not_found() {
        List<CreditApplication> creditApplications=new ArrayList<>();

        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);

        //then
        assertThrows(NotFoundException.class, () -> {
            CreditApplication actualCreditApplication = creditApplicationService.getCreditApplicationByCustomerId(1);
        });
    }

    @Test
    void checkCreditApplicationStatus() {
        //init
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer1 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditApplication creditApplication=new CreditApplication(1,customer,"Approved",10000,new Date());
        CreditApplication creditApplication1=new CreditApplication(1,customer1,"Rejected",0,new Date());
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication1);
        creditApplications.add(creditApplication);
        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);

        //then
        CreditApplication actualCreditApplication = creditApplicationService.checkCreditApplicationStatus("81566338254");
        assertEquals(creditApplication,actualCreditApplication);
    }

    @Test
    void checkCreditApplicationStatus_not_found() {
        //init
        List<CreditApplication> creditApplications=new ArrayList<>();
        //stub-when
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);

        //then
        assertThrows(NotFoundException.class, () -> {
            CreditApplication actualCreditApplication = creditApplicationService.checkCreditApplicationStatus("81566338254");
        });
    }

    @Test
    void checkCreditApplicationResult() {
        //init
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        List<Customer> customers=new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        CreditScore creditScore1=new CreditScore(1,550,customer1);
        CreditScore creditScore2=new CreditScore(2,680,customer2);
        List<CreditScore> creditScores=new ArrayList<>();
        creditScores.add(creditScore1);
        creditScores.add(creditScore2);

        CreditApplication creditApplication=new CreditApplication(1,customer1,"Approved",20000,new Date());
        CreditApplication creditApplication1=new CreditApplication(2,customer2,"Approved",10000,new Date());
        List<CreditApplication>creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication1);
        creditApplications.add(creditApplication);


        //when
        when(customerService.getAllCustomer()).thenReturn(customers);
        when(creditScoreService.getAllCreditScore()).thenReturn(creditScores);
        when(creditScoreService.getCreditScoreByCustomerNationalID("81566338254")).thenReturn(creditScore1);
        when(customerService.getCustomerByNationalID("81566338254")).thenReturn(customer1);
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);
        when(creditApplicationRepository.findById(1)).thenReturn(Optional.of(creditApplication));
        when(creditApplicationRepository.save(creditApplication)).thenReturn(creditApplication);

        //then
        CreditApplication actualCreditApplication = creditApplicationService.checkCreditApplicationResult(customer1);
        assertEquals(creditApplication,actualCreditApplication);
    }
}