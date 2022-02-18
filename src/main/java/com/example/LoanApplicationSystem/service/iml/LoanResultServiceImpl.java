package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.model.entity.Loan;
import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.model.entity.LoanResult;
import com.example.LoanApplicationSystem.repository.LoanResultRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.LoanApplicationService;
import com.example.LoanApplicationSystem.service.LoanResultService;
import com.example.LoanApplicationSystem.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanResultServiceImpl implements LoanResultService {

    @Autowired
    private LoanResultRepository loanResultRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    LoanService loanService;

    @Override
    public void addLoanResult(LoanResult loanResult) {
        loanResultRepository.save(loanResult);
    }

    @Override
    public boolean deleteLoanResult(int id) {
        return false;
    }

    @Override
    public LoanResult updateLoanResult(LoanResult loanResult) {
        return null;
    }

    @Override
    public LoanResult getLoanResultById(int id) {
        Optional<LoanResult> loanResultById = loanResultRepository.findById(id);
        return loanResultById.orElseThrow(()->new RuntimeException("Loan Result doesn't found"));
    }

    @Override
    public List<LoanResult> getAllLoanResult() {
        return loanResultRepository.findAll();
    }



}
