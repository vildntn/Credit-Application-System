package com.example.CreditApplicationSystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "First name cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Identification Number cannot be empty")
    @Column(name = "national_id")
    private String nationalID;

    @NotNull(message = "Phone Number cannot be empty")
    @Column(name = "phone_number")
    private String phoneNumber;

//    @Email
//    @Column(name = "email")
//    private String email;

    @NotNull(message = "Monthly Income cannot be empty")
    @Column(name = "monthly_income")
    private int monthlyIncome;

}
