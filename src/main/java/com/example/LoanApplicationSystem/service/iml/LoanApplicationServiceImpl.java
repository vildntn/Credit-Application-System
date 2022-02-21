package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.repository.LoanApplicationRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.LoanApplicationService;
import com.example.LoanApplicationSystem.service.LoanScoreService;
import com.example.LoanApplicationSystem.service.LoanService;
import com.example.LoanApplicationSystem.service.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerService customerService;


    @Override
    public String addLoanApplication(LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
        return Messages.loanApplicationAdded;
    }

    @Override
    public String deleteLoanApplication(int id) {
        loanApplicationRepository.delete(getLoanApplicationById(id));
        return Messages.loanApplicationDeleted;
    }

    @Override
    public LoanApplication updateLoanApplication(LoanApplication loanApplication) {
        return null;
    }

    @Override
    public LoanApplication getLoanApplicationById(int id) {
        Optional<LoanApplication> loanApplicationById = loanApplicationRepository.findById(id);
        return loanApplicationById.orElseThrow(()->new RuntimeException("Loan Application doesn't found"));
    }

    @Override
    public List<LoanApplication> getAllLoanApplication() {
        return loanApplicationRepository.findAll();
    }

    @Override
    public LoanApplication getLoanApplicationByCustomerId(int id) {
        List<LoanApplication> allLoanApplication = getAllLoanApplication();
        return allLoanApplication.stream()
                .filter((l)->l.getCustomer().getId()==id).findFirst()
                .orElseThrow(()-> new NotFoundException(Messages.loanApplicationDoesntFound));
    }

    @Override
    public LoanApplication checkLoanApplicationStatus(String identificationNumber) {
        Customer customer = customerService.getCustomerByIdentificationNumber(identificationNumber);
        List<LoanApplication> allLoanApplication = getAllLoanApplication();
        return allLoanApplication.stream()
                .filter((l)->l.getCustomer().getIdentificationNumber().equals(identificationNumber))
                .findFirst()
                .orElseThrow(()->new NotFoundException(Messages.loanApplicationDoesntFound));
    }

}
