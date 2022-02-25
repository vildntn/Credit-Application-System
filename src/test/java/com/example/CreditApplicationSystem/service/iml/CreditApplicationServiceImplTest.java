package com.example.CreditApplicationSystem.service.iml;


import com.example.CreditApplicationSystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void deleteCreditApplication() {
    }

    @Test
    void updateCreditApplication() {
    }

    @Test
    void getCreditApplicationById() {
    }

    @Test
    void getAllCreditApplication() {
    }

    @Test
    void getCreditApplicationByCustomerId() {
    }

    @Test
    void checkCreditApplicationStatus() {
    }

    @Test
    void checkCreditApplicationResult() {
    }
}