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


    @Override
    public String checkLoanResultByLoanApplication(int id) {
        LoanApplication loanApplicationById = loanApplicationService.getLoanApplicationById(id);
        LoanResult loanResult=new LoanResult();
        if(isLoanScoreSmallerThan(loanApplicationById.getLoanScore().getLoanScore())){
            checkLoanByApplicationResult(id);
            loanResult.setLoanApplication(loanApplicationById);
            loanResult.setLoanStatus("Approved");
            addLoanResult(loanResult);
            return loanResult.getLoanStatus();
        }
        loanResult.setLoanApplication(loanApplicationById);
        loanResult.setLoanStatus("Rejected");
        addLoanResult(loanResult);
        return loanResult.getLoanStatus();
    }

    private boolean isLoanScoreSmallerThan(int score){
        //Eğer loan score 500'den küçükse false döndür geri kalanı true yap
        if(score<500){
            return false;
        }
        return true;
    }
    /*
    * kullanıcı sisteme geldi ama authentica olmadı sadece başvuru yapacak
    * başvuru sayfasına geldi, burda ona ait tc, aylık kazan, score, ad soyad girmeli
    * bu kısımda başvuru kaydolur ama kullanıcı da kaydolması gerekmez mi?
    * */

    //add loan by loan score and monthly income
    //Ya loanı devre dışı bırak result kısmına ekle ve dto döndür(loan limit ve status)
    //loan devre dışı kalırsa bu method int yani limit döndürmeli, 0 dönsün şartlar sağlanmazsa
    // ya da loan kalsın ama dto döndür(loan limit ve status)
    private void checkLoanByApplicationResult(int id){
        LoanApplication loanApplicationById = loanApplicationService.getLoanApplicationById(id);
        Loan loan=new Loan();
        if ((loanApplicationById.getLoanScore().getLoanScore() >= 500 && loanApplicationById.getLoanScore().getLoanScore() < 1000)) {

            if(loanApplicationById.getCustomer().getMonthlyIncome()<5000){
                loan.setLoanLimit(10000);
                loan.setCustomer(loanApplicationById.getCustomer());
                loanService.addLoan(loan);
            }
            else{
                loan.setLoanLimit(20000);
                loan.setCustomer(loanApplicationById.getCustomer());
                loanService.addLoan(loan);
            }
        }
        if(loanApplicationById.getLoanScore().getLoanScore() >= 1000){
            loan.setLoanLimit(loanApplicationById.getCustomer().getMonthlyIncome()*4);
            loan.setCustomer(loanApplicationById.getCustomer());
            loanService.addLoan(loan);
        }
    }





}
