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

    @Autowired
    private CreditApplicationProducer creditApplicationProducer;

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

    //müşteri zaten varsa onu getirsin yoksa kayıt etsin ve score tanımlasın sonra bu işlemleri yapsın
    /*
     * İsterde müşterinin kayıt olması gerektiği gibi bir zorunluluk yok.
     * Ayrıca eğer sistemde tanımlı customer yoksa loanı bulamıyor.
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
            creditApplicationProducer.sendCreditAppliation(creditApplication.getId());
            return creditApplication;

        }
        CreditApplication creditApplication = setCreditApplication("Rejected", getLoanLimit(customer.getNationalID()), certainCustomer);
        creditApplicationProducer.sendCreditAppliation(creditApplication.getId());
        return creditApplication;
    }


    private boolean isLoanScoreApprovel(int score) {
        //Eğer loan score 500'den küçükse false döndür geri kalanı true yap
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

    private CreditApplication setCreditApplication(String creditStatus, int creditLimit, Customer customer) {
        CreditApplication creditApplication = new CreditApplication();
        if(!checkIfCreditApplicationAlreadyExist(customer.getId())){
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
        return allCreditApplication.stream().anyMatch((l) -> l.getCustomer().getId()==(customerId));
    }

//    private boolean getloanSDeneme(String nationalID){
//        List<LoanApplication> allLoanApplication = getAllLoanApplication();
//        return allLoanApplication.stream()
//                .filter((l) -> l.getCustomer().getNationalID().equals(nationalID))
//                .filter((ln) -> ln.getCustomer().getLoanScore().getLoanScore()>= 500 & ln.getCustomer().getLoanScore().getLoanScore() < 1000)
//                .anyMatch((c) -> c.getCustomer().getMonthlyIncome() < 5000);
//
//    }
//
//    private boolean getloanSecond(String nationalID){
//        List<LoanApplication> allLoanApplication = getAllLoanApplication();
//        return allLoanApplication.stream()
//                .filter((l) -> l.getCustomer().getNationalID().equals(nationalID))
//                .filter((ln) -> ln.getCustomer().getLoanScore().getLoanScore()>= 500 & ln.getCustomer().getLoanScore().getLoanScore() < 1000)
//                .anyMatch((c) -> c.getCustomer().getMonthlyIncome() >= 5000);
//
//    }
//
//    private int getLoanLimitDeneme(String nationalID){
//        CreditScore loanScore = creditScoreService.getLoanScoreByCustomerNationalID(nationalID);
//        Customer customer = customerService.getCustomerByNationalID(nationalID);
//        int loanLimit=0;
//        if (getloanSDeneme(nationalID)) {
//                loanLimit=10000;
//                return loanLimit;
//        }
//       if(getloanSecond(nationalID)){
//            loanLimit=20000;
//            return loanLimit;
//        }
//         if(loanScore.getLoanScore() >= 1000){
//            return customer.getMonthlyIncome()*4;
//        }
//        return loanLimit;
//    }
//

//    private LoanApplication setLoanApplication(String loanStatus,int loanLimit, Customer customer){
//        LoanApplication loanApplication=new LoanApplication();
//        boolean loanApplicationAlreadyExist = chackIfLoanApplicationAlreadyExist(customer.getNationalID());
//        if(!loanApplicationAlreadyExist){
//            loanApplication.setLoanStatus(loanStatus);
//            loanApplication.setLoanLimit(loanLimit);
//            loanApplication.setCustomer(customer);
//            loanApplication.setApplicationDate(new Date());
//            addLoanApplication(loanApplication);
//        }
//        return loanApplication;
//    }

}
