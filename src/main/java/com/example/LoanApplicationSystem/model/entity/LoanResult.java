package com.example.LoanApplicationSystem.model.entity;

import com.example.LoanApplicationSystem.model.entity.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_results")
public class LoanResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    @Column(name = "loan_application_result_date")
    private Date loanApplicationResultDate;

    @Column(name = "loan_status")
    private String loanStatus;
}
