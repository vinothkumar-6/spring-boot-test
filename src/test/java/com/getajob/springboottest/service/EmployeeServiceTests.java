package com.getajob.springboottest.service;

import com.getajob.springboottest.exception.EmployeeException;
import com.getajob.springboottest.model.Employee;
import com.getajob.springboottest.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;


    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().id(1).firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
    }


    //Junit test for saveEmployee
    @Test
    @DisplayName("Junit test for saveEmployee Method")
    public void givenEmployeeDetails_whenSaveEmployee_thenReturnEmployeeObject() {

        //given -precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then - verify the output.
        assertThat(savedEmployee).isNotNull();


    }

    //junit test for saveemployee method which throws exception
    @Test
    @DisplayName("Junit test for saveEmployee Method which throws exception")
    public void givenEmployeeDetails_whenSaveEmployee_thenReturnEmployeeObjectWithException() {

        //given -precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);
        //when - action or the behaviour that we are going test
        //Employee employee1 = employeeService.saveEmployee(employee);
        // assertThat(employee1).isNotNull();
        org.junit.jupiter.api.Assertions.assertThrows(EmployeeException.class, () -> {
            employeeService.saveEmployee(employee);
        });
        //then - verify the output.
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    //junit test for find all employee method

    @Test
    @DisplayName("Junit test to find all employees")
    public void givenEmployeeList_whenGetAllEmployee_thenReturnListOfEmployees() {
        Employee employee1 = Employee.builder().id(1).firstName("vihaan").lastName("kumar").email("vinoth@gmail.com").build();
        //given -precondition or setup
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee));
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        //when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output.
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    //junit test for find all employee method negative scenario

    @Test
    @DisplayName("Junit test to find all employees negative scenario")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnListOfEmployees() {

        //given -precondition or setup
        Employee employee1 = Employee.builder().firstName("vinu").lastName("kumar").email("vinoth@gmail.com").build();
        //when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output.
        Assertions.assertThat(employeeList).isNullOrEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

    //Junit test to find employee by id
    @Test
    @DisplayName("Junit test to find employee by id")
    public void givenEmployeeId_whenFindEmployeeById_thenReturnEmployeeObject() {

        //given -precondition or setup
        given(employeeRepository.findById(1l)).willReturn(Optional.of(employee));
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeService.findEmployeeById(1).get();
        System.out.println(employee1);
        //then - verify the output.
        assertThat(employee1).isNotNull();

    }

    //Junit test to update employee method
    @Test
    @DisplayName("Junit test to update employee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {

        //given -precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("vinothkumar.j@gmailoutlook.com");
        //when - action or the behaviour that we are going test
        Employee employee1 = employeeService.saveEmployee(employee);
        System.out.println(employee1);
        //then - verify the output.
        assertThat(employee1).isNotNull();
        assertThat(employee1.getEmail()).isEqualTo("vinothkumar.j@gmailoutlook.com");
    }

    //junit test to delete employee by id
    @Test
    @DisplayName("junit test to delete employee by id")
        public void givenEmployeeId_whenDeleteEmployeeById_thenReturnsNull(){

            //given -precondition or setup
            willDoNothing().given(employeeRepository).deleteById(1l);
            //when - action or the behaviour that we are going test
        employeeService.deleteEmployee(1L);
            //then - verify the output.
        verify(employeeRepository, times(1)).deleteById(1L);
        }

}
