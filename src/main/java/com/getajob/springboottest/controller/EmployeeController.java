package com.getajob.springboottest.controller;

import com.getajob.springboottest.model.Employee;
import com.getajob.springboottest.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    private List<Employee> employee;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        System.out.println(employee.getFirstName());
        return employeeService.saveEmployee(employee);
    }

    @PostConstruct
    public void setup() {
        employee = new ArrayList<>();
        employee.add(Employee.builder().id(1).firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build());
        employee.add(Employee.builder().id(1).firstName("vsdfnoth").lastName("kumsdfar").email("visxdfnoth@gmail.com").build());
        employee.add(Employee.builder().id(1).firstName("visgfrnoth").lastName("kusdfsfmar").email("vinosdfsdfth@gmail.com").build());
    }

    @GetMapping("/employees")
    public List<Employee> findAllEmployee() {

        employeeService.saveEmployeeList(employee);
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        return employeeService.findEmployeeById(id).map(savedEmployee -> {
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setEmail(employee.getEmail());
            Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") long id){
         employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee Deleted Successfully", HttpStatus.OK);
    }
}
