package com.hahn.erms.services;


import com.hahn.erms.models.Department;
import com.hahn.erms.models.Employee;

import java.util.List;

public interface EmployeeService {


    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByPage(int page, int size);
    List<Employee> getEmployeesByPage(int page, int size, String order);
    List<Employee> getEmployeesByPage(int page, int size, String order, String sortField);
    Employee getEmployeeById(long id);
    void createEmployee(Employee employee);
    void updateEmployee(long id, Employee employee);
    void deleteEmployee(long id);
    List<Department> getAllDepartments();
    List<String> getAllEmploymentStatus();
}
