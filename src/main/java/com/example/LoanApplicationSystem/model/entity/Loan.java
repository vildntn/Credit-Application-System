package com.example.LoanApplicationSystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "loan_limit")
    private int loanLimit;

}
