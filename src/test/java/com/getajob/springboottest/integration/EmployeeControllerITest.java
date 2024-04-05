package com.getajob.springboottest.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getajob.springboottest.model.Employee;
import com.getajob.springboottest.repository.EmployeeRepository;
import com.getajob.springboottest.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Junit test to test rest create employee")
    public void givenEmployeeObject_whenCreateEmployee_thenSavedEmployee() throws Exception {
        Employee employee = Employee.builder().id(1).firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        ResultActions response = null;
        //given -precondition or setup

        //when - action or the behaviour that we are going test

            response = mockMvc.perform(post("/api/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output.
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    @DisplayName("Junit test to return employee list")
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {

        //given -precondition or setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build());
        employeeList.add(Employee.builder().firstName("vsdfnoth").lastName("kumsdfar").email("visxdfnoth@gmail.com").build());
        employeeList.add(Employee.builder().firstName("visgfrnoth").lastName("kusdfsfmar").email("vinosdfsdfth@gmail.com").build());
        employeeRepository.saveAll(employeeList);
        //when - action or the behaviour that we are going test

        ResultActions response = null;
        try {
            response = mockMvc.perform(get("/api/employees"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //then - verify the output.
        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @Test
    @DisplayName("Junit test to return employee by id")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {

        //given -precondition or setup

        Employee employee = Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are
        // going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
        //then - verify the output.
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("Junit test to return employee by id negative scenario")
    public void givenInvalidEmpId_whenGetEmployeeId_thenReturnEmpty() throws Exception {

        //given -precondition or setup
        long id = 1L;
        Employee employee = Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 5));
        //then - verify the output.
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @DisplayName("JUnit test for update Employee positive method")
    public void givenEmployeeId_whenUpdateEMployee_thenReturnUpdateEmployeeObject() throws Exception {

        //given -precondition or setup
        long id = 1L;
        Employee savedEmployee = Employee.builder().email("ram").firstName("vinoth").lastName("kumar").email("vino@gmail.com").build();
        employeeRepository.save(savedEmployee);
        Employee updateEmployee = Employee.builder().email("Vinoth").firstName("kumaruuss").lastName("kumar").email("vino@gmail.com").build();
                 employeeRepository.save(updateEmployee);
        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployee)));

        //then - verify the output.
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updateEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updateEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updateEmployee.getEmail())));

    }

    @Test
    @DisplayName("JUnit test for update Employee negative method")
    public void givenEmployeeId_whenUpdateEmployee_thenReturnEmptyObject() throws Exception {

        //given -precondition or setup
        Employee savedEmployee = Employee.builder().email("ram").firstName("vinodddth").lastName("kumar").email("vino@gmail.com").build();
        employeeRepository.save(savedEmployee);
        Employee updateEmployee = Employee.builder().email("Vinoth").firstName("vinosdffth").lastName("kumar").email("vino@gmail.com").build();
        long id = 10L;

        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployee)));
        //then - verify the output.
        response.andExpect(status().isNotFound()).andDo(print());

    }

    @Test
    @DisplayName("JUnit test for Delete Employee by id")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccess() throws Exception{
        long id = 1L;
        //given -precondition or setup
        Employee savedEmployee = Employee.builder().email("ram").firstName("vinodddth").lastName("kumar").email("vino@gmail.com").build();
           employeeRepository.save(savedEmployee);


        //when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",savedEmployee.getId()));
        //then - verify the output.
        response.andExpect(status().isOk()).andDo(print());

    }
}
