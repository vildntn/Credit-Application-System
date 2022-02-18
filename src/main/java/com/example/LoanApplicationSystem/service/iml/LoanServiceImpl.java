package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.Loan;
import com.example.LoanApplicationSystem.repository.LoanRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public boolean deleteLoan(int id) {
        return false;
    }

    @Override
    public Loan updateLoan(Loan loan) {
        return null;
    }

    @Override
    public List<Loan> getAllLoan() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanByCustomerId(int id) {
       return loanRepository.getLoanByCustomerId(id);
    }

    @Override
    public Loan getLoanById(int id) {
        Optional<Loan> loanById = loanRepository.findById(id);
        return loanById.orElseThrow(()->new RuntimeException("Loan doesn't found"));
    }

    @Override
    public int getLoanLimitByCustomerId(int id) {
        return getLoanByCustomerId(id).getLoanLimit();
    }
}
