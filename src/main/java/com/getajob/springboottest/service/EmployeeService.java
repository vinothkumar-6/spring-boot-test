package com.getajob.springboottest.service;

import com.getajob.springboottest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee saveEmployee(Employee employee);

    public List<Employee> getAllEmployees();

    public Optional<Employee> findEmployeeById(long id);

    public  Employee updateEmployee(Employee employee);

    public void deleteEmployee(long id);

    public void saveEmployeeList(List<Employee> employeeList);
}
