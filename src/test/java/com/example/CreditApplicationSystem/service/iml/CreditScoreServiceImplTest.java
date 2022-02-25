package com.example.CreditApplicationSystem.service.iml;


import com.example.CreditApplicationSystem.repository.CreditScoreRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceImplTest {

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @InjectMocks
    private CreditScoreServiceImpl loanScoreService;

    @Test
    void addCreditScore() {
    }

    @Test
    void deleteCreditScore() {
    }

    @Test
    void getCreditScoreByCustomerNationalID() {
    }

    @Test
    void getCreditScoreById() {
    }

    @Test
    void getAllCreditScore() {
    }

    @Test
    void getCreditScoreByCustomerId() {
    }

    @Test
    void createCreditScoreToCustomer() {
    }
}