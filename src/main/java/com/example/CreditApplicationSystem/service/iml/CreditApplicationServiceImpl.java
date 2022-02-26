package com.example.CreditApplicationSystem.service.iml;

import com.example.CreditApplicationSystem.exception.AlreadyExistException;
import com.example.CreditApplicationSystem.messaging.producer.CreditApplicationProducer;
import com.example.CreditApplicationSystem.service.constant.Messages;
import com.example.CreditApplicationSystem.exception.NotFoundException;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.repository.CreditApplicationRepository;
import com.example.CreditApplicationSystem.service.CustomerService;
import com.example.CreditApplicationSystem.service.CreditApplicationService;
import com.example.CreditApplicationSystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {

    @Autowired
    private CreditApplicationRepository creditApplicationRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CreditScoreService creditScoreService;

//    @Autowired
//    private CreditApplicationProducer creditApplicationProducer;

    @Override
    public void addCreditApplication(CreditApplication creditApplication) {
        creditApplicationRepository.save(creditApplication);
    }

    @Override
    public boolean deleteCreditApplication(int id) {
        creditApplicationRepository.delete(getCreditApplicationById(id));
        return true;
    }

    @Override
    public CreditApplication updateCreditApplication(CreditApplication creditApplication) {
        return creditApplicationRepository.save(creditApplication);
    }

    @Override
    public CreditApplication getCreditApplicationById(int id) {
        Optional<CreditApplication> creditApplication = creditApplicationRepository.findById(id);
        return creditApplication.orElseThrow(() -> new AlreadyExistException(Messages.creditApplicationAlreadyExist));
    }

    @Override
    public List<CreditApplication> getAllCreditApplication() {
        return creditApplicationRepository.findAll();
    }

    @Override
    public CreditApplication getCreditApplicationByCustomerId(int id) {
        List<CreditApplication> creditApplication = getAllCreditApplication();
        return creditApplication.stream()
                .filter((l) -> l.getCustomer().getId() == id).findFirst()
                .orElseThrow(() -> new NotFoundException(Messages.creditApplicationDoesntFound));
    }

    @Override
    public CreditApplication checkCreditApplicationStatus(String nationalID) {
        //Customer customer = customerService.getCustomerByNationalID(nationalID);
        List<CreditApplication> creditApplication = getAllCreditApplication();
        return creditApplication.stream()
                .filter((l) -> l.getCustomer().getNationalID().equals(nationalID))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Messages.creditApplicationDoesntFound));
    }

    /*
    * An application was requested based on customer information.
    * Considering the customer's non-registration, it is designed to be the first registration if there is no customer.
    * In case of compliance with the rules, the credit application is created and a credit limit is assigned to the customer.
    * Direct sending service to rabbitmq of the recorded loan application for sms simulation has been written.
    * But then it was converted to controller.
    * */
    @Override
    public CreditApplication checkCreditApplicationResult(Customer customer) {
        if (!customerService.checkIfCustomerAlreadyExist(customer.getNationalID())) {
            customerService.addCustomer(customer);
        }
        Customer certainCustomer = customerService.getCustomerByNationalID(customer.getNationalID());
        CreditScore creditScore = creditScoreService.getCreditScoreByCustomerNationalID(customer.getNationalID());
        if (isLoanScoreApprovel(creditScore.getCreditScore())) {
            CreditApplication creditApplication = setCreditApplication("Approved", getLoanLimit(customer.getNationalID()), certainCustomer);
            //creditApplicationProducer.sendCreditAppliation(creditApplication);
            return creditApplication;

        }
        CreditApplication creditApplication = setCreditApplication("Rejected", getLoanLimit(customer.getNationalID()), certainCustomer);
        //creditApplicationProducer.sendCreditAppliation(creditApplication);
        return creditApplication;
    }

    /*It is a helpful method for direct rejection of the user if the credit score is below 500.*/
    private boolean isLoanScoreApprovel(int score) {
        if (score < 500) {
            return false;
        }
        return true;
    }

    private int getLoanLimit(String nationalID) {
        CreditScore creditScore = creditScoreService.getCreditScoreByCustomerNationalID(nationalID);
        Customer customer = customerService.getCustomerByNationalID(nationalID);
        int loanLimit = 0;
        if ((creditScore.getCreditScore() >= 500 && creditScore.getCreditScore() < 1000)) {

            if (customer.getMonthlyIncome() < 5000) {
                loanLimit = 10000;
                return loanLimit;
            } else {
                loanLimit = 20000;
                return loanLimit;
            }
        }
        if (creditScore.getCreditScore() >= 1000) {
            return customer.getMonthlyIncome() * 4;
        }
        return loanLimit;
    }

    /*
    * Since the same operations can be performed in more than one place, a method has been collected.
    * According to the information received, if there is no loan application, it is recorded.
    * */
    private CreditApplication setCreditApplication(String creditStatus, int creditLimit, Customer customer) {
        CreditApplication creditApplication = new CreditApplication();
        if (!checkIfCreditApplicationAlreadyExist(customer.getId())) {
            creditApplication.setCreditStatus(creditStatus);
            creditApplication.setCreditLimit(creditLimit);
            creditApplication.setCustomer(customer);
            creditApplication.setApplicationDate(new Date());
            addCreditApplication(creditApplication);
        }
        return creditApplication;
    }


    private boolean checkIfCreditApplicationAlreadyExist(int customerId) {
        List<CreditApplication> allCreditApplication = getAllCreditApplication();
        return allCreditApplication.stream().anyMatch((l) -> l.getCustomer().getId() == (customerId));
    }

}
