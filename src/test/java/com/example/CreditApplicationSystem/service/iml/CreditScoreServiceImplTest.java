package com.example.CreditApplicationSystem.service.iml;


import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.repository.CreditScoreRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceImplTest {

    @Mock
    private CreditScoreRepository creditScoreRepository;


    @InjectMocks
    private CreditScoreServiceImpl creditScoreService;

    @Test
    void addCreditScore() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditScore creditScore=new CreditScore(1,450,expectedCustomer);

        //stub-when
        when(creditScoreRepository.save(creditScore)).thenReturn(creditScore);

        //then
        creditScoreService.addCreditScore(creditScore);
        verify(creditScoreRepository, times(1)).save(creditScore);

    }
    @Test
    void updateCreditScore() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditScore expectedCreditScore=new CreditScore(1,450,expectedCustomer);
        expectedCreditScore.setCreditScore(500);
        //stub - when
        when(creditScoreRepository.save(expectedCreditScore)).thenReturn(expectedCreditScore);

        //then
        CreditScore actualCreditScore = creditScoreService.updateCreditScore(expectedCreditScore);

        assertEquals(expectedCreditScore, actualCreditScore);
    }

    @Test
    void deleteCreditScore() {
        //init step
        Customer expectedCustomer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditScore creditScore=new CreditScore(1,450,expectedCustomer);

        //stub-when
        when(creditScoreRepository.findById(1)).thenReturn(Optional.of(creditScore));

        //then
        boolean deletedCreditScore = creditScoreService.deleteCreditScore(1);
        assertTrue(deletedCreditScore);

    }

    @Test
    void deleteCreditScore_not() {
        //stub-when
        when(creditScoreRepository.findById(1)).thenReturn(Optional.empty());

        //then
        assertThrows(NotFoundException.class, () -> {
            boolean deletedCreditScore = creditScoreService.deleteCreditScore(1);
        });

    }

    @Test
    void getCreditScoreByCustomerNationalID() {
        //init step
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditScore creditScore1=new CreditScore(1,450,customer1);
        CreditScore creditScore2=new CreditScore(2,680,customer2);
        List<CreditScore> creditScores=new ArrayList<>();
        creditScores.add(creditScore1);
        creditScores.add(creditScore2);

        //stub-when
        when(creditScoreService.getAllCreditScore()).thenReturn(creditScores);

        //then step
        CreditScore actualCreditScore = creditScoreService.getCreditScoreByCustomerNationalID("81566338254");

        assertEquals(creditScore1.getCreditScore(),actualCreditScore.getCreditScore());
       // assertEquals(actualCreditScore.getCustomer().getNationalID(),"81566338254");


    }
    @Test
    void getCreditScoreByCustomerNationalID_not_found() {
        //init step
        List<CreditScore> creditScores=new ArrayList<>();
        //stub-when
        when(creditScoreService.getAllCreditScore()).thenReturn(creditScores);
        //then step
        assertThrows(NotFoundException.class, () -> {
            CreditScore actualCreditScore = creditScoreService.getCreditScoreByCustomerNationalID("81566338254");
        });
    }

    @Test
    void getCreditScoreById_successful() {
        //init step
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditScore expectedCreditScore=new CreditScore(1,450,customer);

        //stub-when
        when(creditScoreRepository.findById(1)).thenReturn(Optional.of(expectedCreditScore));

        //then
        CreditScore actualCreditScore = creditScoreService.getCreditScoreById(1);

        assertEquals(expectedCreditScore,actualCreditScore);

    }

    @Test
    void getCreditScoreById_not_found() {
        //stub-when
        when(creditScoreRepository.findById(1)).thenReturn(Optional.empty());

        //then step
        assertThrows(NotFoundException.class, () -> {
            CreditScore actualCreditScore = creditScoreService.getCreditScoreById(1);
        });
    }

    @Test
    void getAllCreditScore_successful() {
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditScore creditScore1=new CreditScore(1,450,customer1);
        CreditScore creditScore2=new CreditScore(2,680,customer2);
        List<CreditScore> creditScores=new ArrayList<>();
        creditScores.add(creditScore1);
        creditScores.add(creditScore2);

        //stub-when
        when(creditScoreRepository.findAll()).thenReturn(creditScores);

        //then step
        List<CreditScore> allCreditScore = creditScoreService.getAllCreditScore();
        assertEquals(creditScores,allCreditScore);

    }

    @Test
    void getAllCreditScore_empty() {
        List<CreditScore> creditScores=new ArrayList<>();

        //stub-when
        when(creditScoreRepository.findAll()).thenReturn(creditScores);

        //then step
        List<CreditScore> allCreditScore = creditScoreService.getAllCreditScore();
        assertEquals(0,allCreditScore.size());

    }

    @Test
    void createCreditScoreToCustomer() {
        //init step
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        CreditScore creditScore =new CreditScore();
        int score = 600;
        creditScore.setCreditScore(score);
        creditScore.setCustomer(customer1);
        //stub-when
        when(creditScoreRepository.save(creditScore)).thenReturn(creditScore);
        //then step
        creditScoreService.addCreditScore(creditScore);
        verify(creditScoreRepository, times(1)).save(creditScore);

    }

    @Test
    void createCreditScoreToCustomer_not() {
        //init step

        CreditScore creditScore =new CreditScore();

        //stub-when
        when(creditScoreRepository.save(creditScore)).thenReturn(creditScore);
        //then step
        creditScoreService.addCreditScore(creditScore);
        verify(creditScoreRepository, atLeastOnce()).save(creditScore);

    }
}