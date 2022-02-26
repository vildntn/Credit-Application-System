package com.example.CreditApplicationSystem.service.iml;

import com.example.CreditApplicationSystem.repository.CreditScoreRepository;
import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.CreditScoreService;
import com.example.CreditApplicationSystem.service.constant.Messages;
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
                .findAny().
                orElseThrow(()->new NotFoundException(Messages.creditScoreNotFound));
    }
    @Override
    public CreditScore getCreditScoreById(int id) {
        Optional<CreditScore> creditScoreById = creditScoreRepository.findById(id);
        return creditScoreById.orElseThrow(()->new NotFoundException(Messages.creditScoreNotFound));
    }

    @Override
    public List<CreditScore> getAllCreditScore() {
        return creditScoreRepository.findAll();
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
