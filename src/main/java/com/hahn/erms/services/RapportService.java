package com.hahn.erms.services;

import com.hahn.erms.models.Employee;

import java.util.List;

public interface RapportService {

    void generatePdf(List<Employee> employees);
    void generateXlsx(List<Employee> employees);
}
