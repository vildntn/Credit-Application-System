package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.model.entity.CreditScore;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.repository.CreditScoreRepository;
import com.example.LoanApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Autowired
    private CreditScoreRepository creditScoreRepository;
    

    @Override
    public void addCreditScore(CreditScore creditScore) {
        creditScoreRepository.save(creditScore);
    }

    @Override
    public boolean deleteCreditScore(int id) {
        creditScoreRepository.delete(getCreditScoreById(id));
        return true;
    }


    @Override
    public CreditScore getCreditScoreByCustomerNationalID(String nationalID) {
        List<CreditScore> allCreditScore = getAllCreditScore();
        return allCreditScore.stream()
                .filter((l)->l.getCustomer().getNationalID().equals(nationalID))
                .findAny().orElseThrow(()->new NotFoundException("Credit Score doesn't found"));
    }
    @Override
    public CreditScore getCreditScoreById(int id) {
        Optional<CreditScore> creditScoreById = creditScoreRepository.findById(id);
        return creditScoreById.orElseThrow(()->new RuntimeException("Credit Score doesn't found"));
    }

    @Override
    public List<CreditScore> getAllCreditScore() {
        return creditScoreRepository.findAll();
    }

    @Override
    public int getCreditScoreByCustomerId(int id) {
        return creditScoreRepository.getCreditScoreByCustomerId(id);
    }

    @Override
    public void createCreditScoreToCustomer(Customer customer) {
        CreditScore creditScore =new CreditScore();
        int score = (int)Math.floor(Math.random()*(1500-200+1)+200);
        creditScore.setCreditScore(score);
        creditScore.setCustomer(customer);
        addCreditScore(creditScore);
    }

}
