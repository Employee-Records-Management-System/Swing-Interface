package com.hahn.erms.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;

    private String fullName;

    private String employeeId;

    private String jobTitle;

    private Department department;

    private LocalDate hireDate;

    private String employmentStatus;

    private ContactInfo contactInfo;

    private String address;

    private Gender gender;

    private ContractType contractType;



}

