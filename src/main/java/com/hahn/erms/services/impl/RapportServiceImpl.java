package com.hahn.erms.services.impl;

import com.hahn.erms.models.Employee;
import com.hahn.erms.services.RapportService;

import java.util.List;

public class RapportServiceImpl implements RapportService {


    public void generatePdf(List<Employee> employees) {
        try {
            employees.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateXlsx(List<Employee> employees) {

    }
}
