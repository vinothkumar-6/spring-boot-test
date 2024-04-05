package com.getajob.springboottest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getajob.springboottest.model.Employee;
import com.getajob.springboottest.service.EmployeeService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMVc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {

    }


    @Test
    @DisplayName("Junit test to test rest create employee")
    public void givenEmployeeObject_whenCreateEmployee_thenSavedEmployee() throws Exception {
        Employee employee = Employee.builder().id(1).firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        ResultActions response = null;
        //given -precondition or setup
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or the behaviour that we are going test
        try {
            response = mockMVc.perform(post("/api/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)));
        } catch (JsonProcessingException e) {
            // Handle the exception (e.g., log it, throw a RuntimeException, etc.)
            e.printStackTrace();
        }
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
        employeeList.add(Employee.builder().id(1).firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build());
        employeeList.add(Employee.builder().id(1).firstName("vsdfnoth").lastName("kumsdfar").email("visxdfnoth@gmail.com").build());
        employeeList.add(Employee.builder().id(1).firstName("visgfrnoth").lastName("kusdfsfmar").email("vinosdfsdfth@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(employeeList);
        //when - action or the behaviour that we are going test
        ResultActions response = null;
        try {
            response = mockMVc.perform(get("/api/employees"));
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
        long id = 1L;
        Employee employee = Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        given(employeeService.findEmployeeById(id)).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are
        // going test
        ResultActions response = mockMVc.perform(get("/api/employees/{id}", id));
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
        given(employeeService.findEmployeeById(id)).willReturn(Optional.empty());
        //when - action or the behaviour that we are going test
        ResultActions response = mockMVc.perform(get("/api/employees/{id}", id));
        //then - verify the output.
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @DisplayName("JUnit test for update Employee positive method")
    public void givenEmployeeId_whenUpdateEMployee_thenReturnUpdateEmployeeObject() throws Exception {

        //given -precondition or setup
        long id = 1L;
        Employee savedEmployee = Employee.builder().email("ram").lastName("kumar").email("vino@gmail.com").build();
        Employee updateEmployee = Employee.builder().email("Vinoth").lastName("kumar").email("vino@gmail.com").build();
        given(employeeService.findEmployeeById(id)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or the behaviour that we are going test
        ResultActions response = mockMVc.perform(put("/api/employees/{id}", id)
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
        Employee savedEmployee = Employee.builder().email("ram").lastName("kumar").email("vino@gmail.com").build();
        Employee updateEmployee = Employee.builder().email("Vinoth").lastName("kumar").email("vino@gmail.com").build();
        long id = 1L;
        Employee employee = Employee.builder().firstName("vinoth").lastName("kumar").email("vinoth@gmail.com").build();
        given(employeeService.findEmployeeById(id)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or the behaviour that we are going test
        ResultActions response = mockMVc.perform(put("/api/employees/{id}", id)
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
        willDoNothing().given(employeeService).deleteEmployee(id);

            //when - action or the behaviour that we are going test
     ResultActions response = mockMVc.perform(delete("/api/employees/{id}",id));
            //then - verify the output.
        response.andExpect(status().isOk()).andDo(print());

        }

}
