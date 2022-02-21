package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.model.entity.LoanScore;
import com.example.LoanApplicationSystem.repository.LoanScoreRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.LoanScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanScoreServiceImpl implements LoanScoreService {

    @Autowired
    private LoanScoreRepository loanScoreRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public void addLoanScore(LoanScore loanScore) {
        loanScoreRepository.save(loanScore);
    }

    @Override
    public boolean deleteLoanScore(int id) {
        loanScoreRepository.delete(getLoanScoreById(id));
        return true;
    }

    @Override
    public LoanScore updateLoanScore(LoanScore loanScore) {
        return null;
    }

    @Override
    public LoanScore getLoanScoreById(int id) {
        Optional<LoanScore> loanScoreById = loanScoreRepository.findById(id);
        return loanScoreById.orElseThrow(()->new RuntimeException("Loan Score doesn't found"));
    }

    @Override
    public List<LoanScore> getAllLoanScore() {
        return loanScoreRepository.findAll();
    }

    @Override
    public int getLoanScoreByCustomerId(int id) {
        return loanScoreRepository.getLoanScoreByCustomerId(id);
    }

    @Override
    public LoanScore getLoanScoreByCustomerIdentNumber(String identificationNumber) {
        List<LoanScore> allLoanScore = getAllLoanScore();
        return allLoanScore.stream()
                .filter((l)->l.getCustomer().getIdentificationNumber().equals(identificationNumber))
                .findAny().orElseThrow(()->new NotFoundException("Loan Score doesn't found"));
    }

//    private LoanScore createLoanScoreToCustomerId(int customerId){
//
//    }

    private int createRandomLoanScore(){
        int random_int = (int)Math.floor(Math.random()*(1000-200+1)+200);
        return random_int;
    }
}
