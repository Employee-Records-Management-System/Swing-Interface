package com.hahn.erms.services.impl;


import com.hahn.erms.models.Department;
import com.hahn.erms.models.Employee;
import com.hahn.erms.services.EmployeeService;
import com.hahn.erms.utils.FetcherHelper;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
    private final FetcherHelper<Employee> employeeFetcher;
    private final FetcherHelper<Department> departmentFetcher;
    private final FetcherHelper<String> employmentStatusFetcher;

    public EmployeeServiceImpl(){
        this.employmentStatusFetcher = new FetcherHelper<>(String.class,  "/employees/employment-status");
        this.departmentFetcher = new FetcherHelper<>(Department.class, "/departments");
        this.employeeFetcher = new FetcherHelper<>(Employee.class, "/employees");
    }

    public List<Employee> getAllEmployees() {
        return employeeFetcher.fetchListOfData();
    }

    public List<Employee> getEmployeesByPage(int page, int size) {
        return employeeFetcher.fetchListOfData(Map.of(
                "page", String.valueOf(page),
                "size", String.valueOf(size)
        ));
    }
    public List<Employee> getEmployeesByPage(int page, int size, String order, String sortField) {
        return employeeFetcher.fetchListOfData(Map.of(
                "page", String.valueOf(page),
                "size", String.valueOf(size),
                "sortField", sortField,
                "direction", order
        ));
    }
    public List<Employee> getEmployeesByPage(int page, int size, String order) {
        return employeeFetcher.fetchListOfData(Map.of(
                "page", String.valueOf(page),
                "size", String.valueOf(size),
                "direction", order
        ));
    }

    public Employee getEmployeeById(long id) {
        return employeeFetcher.fetchData(id);
    }

    public void createEmployee(Employee employee) {
        employeeFetcher.createData(employee);
    }

    public void updateEmployee(long id, Employee employee) {
        employeeFetcher.patchData(id, employee);
    }

    public void deleteEmployee(long id) {
        employeeFetcher.deleteData(id);

    }

    public List<Department> getAllDepartments() {
        return departmentFetcher.fetchListOfData();
    }

    public List<String> getAllEmploymentStatus() {
        return employmentStatusFetcher.fetchListOfData();
    }
}
