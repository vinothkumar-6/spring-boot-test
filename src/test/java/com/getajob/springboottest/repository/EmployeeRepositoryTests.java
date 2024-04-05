package com.getajob.springboottest.repository;

import com.getajob.springboottest.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
    }

    //junit test for save employee
//@DisplayName("Junit test for save Employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given -precondition or setup
        //Employee employee = Employee.builder().firstName("Vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output.
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //Junit test for listing all employees
    @Test
    @DisplayName("Junit test for finding all employees")
    public void givenEmployeeListObject_whenFindAllEmployees_thenReturnSavedEmployeeList() {

        //given pre-conditio   n or setup
       // Employee employee1 = Employee.builder().firstName("Vinoth").lastName("kumar").email("vinoth@gmail.com").build();
      //  Employee employee2 = Employee.builder().firstName("vihhan").lastName("kumar").email("vinoth@gmail.com").build();
      //  Employee employee3 = Employee.builder().firstName("suha").lastName("kumar").email("vinoth@gmail.com").build();
      //  Employee employee4 = Employee.builder().firstName("sathish").lastName("kumar").email("vinoth@gmail.com").build();
//Employee employee5 = Employee.builder().firstName("geetha").lastName("kumar").email("vinoth@gmail.com").build();

        employeeRepository.save(employee);
      //  employeeRepository.save(employee2);
       // employeeRepository.save(employee3);
      //  employeeRepository.save(employee4);
      //  employeeRepository.save(employee5);
        //
        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).size().isEqualTo(1);
    }


    @Test
    @DisplayName("Junit test for find employee by id")
    public void givenEmployeeListObject_whenFindEmployeeById_thenReturnEmployee() {

        //given -precondition or setup
//        Employee employee1 = Employee.builder().firstName("Vinoth").lastName("kumar").email("vinoth@gmail.com").id(1).build();
//        Employee employee2 = Employee.builder().firstName("vihhan").lastName("kumar").email("vinoth@gmail.com").id(2).build();
//        Employee employee3 = Employee.builder().firstName("suha").lastName("kumar").email("vinoth@gmail.com").id(3).build();
//        Employee employee4 = Employee.builder().firstName("sathish").lastName("kumar").email("vinoth@gmail.com").id(4).build();
//        Employee employee5 = Employee.builder().firstName("geetha").lastName("kumar").email("vinoth@gmail.com").id(5).build();

        employeeRepository.save(employee);
//        employeeRepository.save(employee2);
//        employeeRepository.save(employee3);
//        employeeRepository.save(employee4);
//        employeeRepository.save(employee5);
        //
        List<Employee> employeeList = employeeRepository.findAll();
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeRepository.findById(employee.getId()).get();
        //then - verify the output.
        assertThat(employee1).isNotNull();
        assertThat(employee1.getId()).isEqualTo(employee.getId());

    }

    //Junit test to get employee by email operation.
    @Test
    @DisplayName("Junit test for find employee by email")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given -precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output.
        assertThat(employee1).isNotNull();
        assertThat(employee1.getEmail()).isEqualTo("vinoth@gmail.com");


    }

    //Junit test for update employee
    @Test
    @DisplayName("Junit test for update employee")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given -precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("vinothkumar.j@outlook.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then - verify the output.
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo(employee.getEmail());

    }


    //Junit test for deleting an employee
    @Test
    @DisplayName("Junit test for deleting an employee")
    public void givenEmployeeObject_whenDeleteEmployee_thenReturnSuccessMessage() {

        //given -precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employee1 = employeeRepository.findById(employee.getId());
        //then - verify the output.

        assertThat(employee1).isEmpty();

    }
   // Junit test for custom query for JPQL with index
    @Test
    @DisplayName("Junit test for custom query for JPQL with index")
    public void givenFirstLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        //given -precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeRepository.findByJPQL("vinoth","kumar");
        //then - verify the output.
        assertThat(employee1.getFirstName()).isEqualTo("vinoth");
        assertThat(employee1).isNotNull();

    }

    // Junit test for custom query for JPQL with named
    @Test
    @DisplayName("Junit test for custom query for JPQL with named")
    public void givenFirstLastName_whenFindByJPQLNamedParam_thenReturnEmployeeObject() {

        //given -precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeRepository.findByJPQLNamedParam("vinoth","kumar");
        //then - verify the output.
        assertThat(employee1.getFirstName()).isEqualTo("vinoth");
        assertThat(employee1).isNotNull();

    }

    // Junit test for custom query for native sql with index
    @Test
    @DisplayName("Junit test for custom query for native sql with index")
        public void givenFirstLastName_whenFindByNativeQuery_thenReturnEmployeeObject(){

            //given -precondition or setup
//           Employee employee = Employee.builder().firstName("Vinoth").lastName("Jayandiran").email("vinoth@gmail.com").build();
           employeeRepository.save(employee);
            //when - action or the behaviour that we are going test
            Employee returnedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());
            //then - verify the output.
        assertThat(returnedEmployee).isNotNull();
        assertThat(returnedEmployee.getFirstName()).isEqualTo(employee.getFirstName());

        }

    // Junit test for custom query for native sql with named parameters
    @Test
    @DisplayName("Junit test for custom query for native sql with named paramater")
        public void givenFirstNameLastName_whenFindByNativeNamedQuery_thenReturnEmployeeObject(){

            //given -precondition or setup
       // Employee employee= Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        employeeRepository.save(employee);
            //when - action or the behaviour that we are going test
           Employee employee1 = employeeRepository.findByNativeNamedParam("vinoth","kumar");
            //then - verify the output.
        assertThat(employee1).isNotNull();
        assertThat(employee1.getFirstName()).isEqualTo("vinoth");

        }


}
