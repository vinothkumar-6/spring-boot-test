package com.getajob.springboottest.service;

import com.getajob.springboottest.exception.EmployeeException;
import com.getajob.springboottest.model.Employee;
import com.getajob.springboottest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository =employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
         Optional<Employee> employee1 = employeeRepository.findByEmail(employee.getEmail());
         if(employee1.isPresent()){
             throw new EmployeeException("Employee already exist with given email: "+employee.getEmail());
         }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        //List<Employee> employeeList = employeeRepository.findAll();
//        if(employeeList.isEmpty()){
//            throw new EmployeeException("No employees found");
//        }
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
         return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
         employeeRepository.deleteById(id);
    }

    @Override
    public void saveEmployeeList(List<Employee> employeeList) {
        employeeRepository.saveAll(employeeList);
    }
}
