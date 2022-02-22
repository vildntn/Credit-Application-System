package com.example.LoanApplicationSystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //OneToOne()
    @ManyToOne()
    @JoinColumn(name = "customer_id",referencedColumnName="id")
    private Customer customer;

//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "loan_score_id", referencedColumnName = "id")
//    private LoanScore loanScore;

    @Column(name = "loan_status")
    private String loanStatus;

    @Column(name = "loan_limit")
    private int loanLimit;

//    @Column(name = "application_date")
//    private Date applicationDate;

//    @Column(name = "status")
//    private boolean status;


}
