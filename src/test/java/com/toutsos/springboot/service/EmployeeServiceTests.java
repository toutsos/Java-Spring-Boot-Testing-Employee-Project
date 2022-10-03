package com.toutsos.springboot.service;

import com.toutsos.springboot.model.Employee;
import com.toutsos.springboot.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    public void setup(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // for save Employee method
    @DisplayName("JUNit test for saveEMployee method")
    @Test
    public void givenEmployeeObj_whenSaveEmployee_thenReturnEmployeeObj(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee))
                .willReturn(employee);
        //Check that mocks successfully created
        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behaviour that we are going to test

        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }

}
