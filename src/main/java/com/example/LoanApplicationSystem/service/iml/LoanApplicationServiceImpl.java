package com.example.LoanApplicationSystem.service.iml;

import com.example.LoanApplicationSystem.exception.NotFoundException;
import com.example.LoanApplicationSystem.model.entity.Customer;
import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import com.example.LoanApplicationSystem.model.entity.LoanScore;
import com.example.LoanApplicationSystem.repository.LoanApplicationRepository;
import com.example.LoanApplicationSystem.service.CustomerService;
import com.example.LoanApplicationSystem.service.LoanApplicationService;
import com.example.LoanApplicationSystem.service.LoanScoreService;
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

    @Autowired
    private LoanScoreService loanScoreService;

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

    //HATA: null value in column "identification_number" of relation "loan_applications" violates not-null constraint
    //  Ayrıntı: Hata veren satır (2, null, Approved, 20000) içeriyor.
    //customer gönderirken id göndermezsem hata alıyorum. Identity ile bağlasam bile göndermezsem id'sini hata alıyorum
    //ama zaten id ile değil identification ile bağlamıştım???
    //müşteri zaten varsa onu getirsin yoksa kayıt etsin ve score tanımlasın sonra bu işlemleri yapsın

    @Override
    public LoanApplication checkLoanApplicationResult(Customer customer) {
        LoanScore loanScore = loanScoreService.getLoanScoreByCustomerIdentNumber(customer.getIdentificationNumber());
        Customer certainCustomer = customerService.getCustomerByIdentificationNumber(customer.getIdentificationNumber());
        LoanApplication loanApplication=new LoanApplication();
        if(isLoanScoreApprovel(loanScore.getLoanScore())){

            loanApplication.setLoanLimit(checkLoanByApplicationResult(customer.getIdentificationNumber()));
            loanApplication.setCustomer(certainCustomer);
            loanApplication.setLoanStatus("Approved");
            addLoanApplication(loanApplication);
            return loanApplication;
        }
        loanApplication.setLoanLimit(checkLoanByApplicationResult(customer.getIdentificationNumber()));
        loanApplication.setCustomer(certainCustomer);
        loanApplication.setLoanStatus("Rejected");
        addLoanApplication(loanApplication);
        return loanApplication;
    }


    private boolean isLoanScoreApprovel(int score){
        //Eğer loan score 500'den küçükse false döndür geri kalanı true yap
        if(score<500){
            return false;
        }
        return true;
    }
    private int checkLoanByApplicationResult(String identification){
        LoanScore loanScore = loanScoreService.getLoanScoreByCustomerIdentNumber(identification);
        Customer customer = customerService.getCustomerByIdentificationNumber(identification);
        int loanLimit=0;
        if ((loanScore.getLoanScore() >= 500 && loanScore.getLoanScore() < 1000)) {

            if(customer.getMonthlyIncome()<5000){
                loanLimit=10000;
                return loanLimit;
            }
            else{
                loanLimit=20000;
                return loanLimit;
            }
        }
        if(loanScore.getLoanScore() >= 1000){
            return customer.getMonthlyIncome()*4;
        }
        return loanLimit;
    }


}
