package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.messaging.producer.CreditApplicationProducer;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.CreditApplication;
import com.example.LoanApplicationSystem.model.entity.CreditScore;
import com.example.LoanApplicationSystem.repository.CreditApplicationRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.CreditApplicationService;
import com.example.LoanApplicationSystem.service.CreditScoreService;
import com.example.LoanApplicationSystem.service.constant.Messages;
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
    public String addCreditApplication(CreditApplication creditApplication) {
        creditApplicationRepository.save(creditApplication);
        return Messages.loanApplicationAdded;
    }

    @Override
    public String deleteCreditApplication(int id) {
        creditApplicationRepository.delete(getCreditApplicationById(id));
        return Messages.loanApplicationDeleted;
    }

    @Override
    public CreditApplication updateCreditApplication(CreditApplication creditApplication) {
        return null;
    }

    @Override
    public CreditApplication getCreditApplicationById(int id) {
        Optional<CreditApplication> creditApplication = creditApplicationRepository.findById(id);
        return creditApplication.orElseThrow(() -> new RuntimeException("Credit Application doesn't found"));
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
                .orElseThrow(() -> new NotFoundException(Messages.loanApplicationDoesntFound));
    }

    @Override
    public CreditApplication checkCreditApplicationStatus(String nationalID) {
        Customer customer = customerService.getCustomerByNationalID(nationalID);
        List<CreditApplication> creditApplication = getAllCreditApplication();
        return creditApplication.stream()
                .filter((l) -> l.getCustomer().getNationalID().equals(nationalID))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Messages.loanApplicationDoesntFound));
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
            CreditApplication loanApplication = setLoanApplication("Approved", getLoanLimit(customer.getNationalID()), certainCustomer);
            creditApplicationProducer.sendLoanAppliation(loanApplication.getId());
            return loanApplication;

        }
        CreditApplication loanApplication = setLoanApplication("Rejected", getLoanLimit(customer.getNationalID()), certainCustomer);
        creditApplicationProducer.sendLoanAppliation(loanApplication.getId());
        return loanApplication;
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

    private CreditApplication setLoanApplication(String loanStatus, int loanLimit, Customer customer) {
        CreditApplication creditApplication = new CreditApplication();
        creditApplication.setCreditStatus(loanStatus);
        creditApplication.setCreditLimit(loanLimit);
        creditApplication.setCustomer(customer);
        creditApplication.setApplicationDate(new Date());
        addCreditApplication(creditApplication);
        return creditApplication;
    }


//    private boolean chackIfLoanApplicationAlreadyExist(String nationalID) {
//        List<LoanApplication> allLoanApplication = getAllLoanApplication();
//        return allLoanApplication.stream().anyMatch((l) -> l.getCustomer().getNationalID().equals(nationalID));
//    }

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
