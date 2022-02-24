package com.example.LoanApplicationSystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_applications")
public class CreditApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
   // @ManyToOne()
    @JoinColumn(name = "customer_id",referencedColumnName="id")
    private Customer customer;

//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "loan_score_id", referencedColumnName = "id")
//    private CreditScore loanScore;

    @Column(name = "credit_status")
    private String creditStatus;

    @Column(name = "credit_limit")
    private int creditLimit;

    @Column(name = "application_date")
    private Date applicationDate;

//    @Column(name = "status")
//    private boolean status;


}
