package com.toutsos.springboot.service;

import com.toutsos.springboot.exception.ResourceNotFoundException;
import com.toutsos.springboot.model.Employee;
import com.toutsos.springboot.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
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

import java.util.Collection;
import java.util.Collections;
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
    public void setup(){
        /**
         * the next 2 lines replaced from annotations @Mock , @InjectMock & @ExtendWith(MockitoExtension.class)
         */
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();
    }

    // for save Employee method
    @DisplayName("JUNit test for saveEMployee method")
    @Test
    public void givenEmployeeObj_whenSaveEmployee_thenReturnEmployeeObj(){
        //given - precondition or setup;
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee))
                .willReturn(employee);
        //Check that mocks successfully created
        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behaviour that we are going to test

        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
    @DisplayName("JUNit test for saveEMployee method which throws exc")
    @Test
    public void givenExistinEmail_whenSaveEmployee_thenThrowsException(){
        //given - precondition or setup;
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });

        //then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployess method
    @DisplayName("JUNit test getAllEmployess method")
    @Test
    public void givenEmployeesList_whenGetAllEmployess_thenGetAllEmployess() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Angelos2")
                .lastName("Toutsios2")
                .email("angelos2.toutsios@gmail.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(List.of(employee,employee1));

        //when - action or the behaviour that we are going to test
        List<Employee> employeesList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeesList).isNotNull();
        assertThat(employeesList.size()).isEqualTo(2);
    }

    //JUnit test for getAllEmployess invalid method
    @DisplayName("JUNit test getAllEmployess invalid method (negative scenario)")
    @Test
    public void givenEmptyloyeesList_whenGetAllEmployess_thenReturnEmptyList() {
        //given - precondition or setup

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        //when - action or the behaviour that we are going to test
        List<Employee> employeesList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeesList).isEmpty();
        assertThat(employeesList.size()).isEqualTo(0);
    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUNit test getAllEmployess invalid method (negative scenario)")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        //given - precondition or setup
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test

        Optional<Employee> returnedEmployee = employeeService.getEmployeeById(employee.getId());

        //then - verify the output
        assertThat(returnedEmployee.get()).isNotNull();
    }

    //JUnit test for updateEmployee method
    @DisplayName("JUNit test updateEmployee method")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployeeMethod_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("updatedEmail");
        employee.setFirstName("updatedFirstname");
        employee.setLastName("updatedLastname");


        //when - action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("updatedEmail");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("updatedFirstname");
        assertThat(updatedEmployee.getLastName()).isEqualTo("updatedLastname");
    }

    //JUnit test for deleteEmployee method
    @DisplayName("JUNit test deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteById_thenNothing() {
        //given - precondition or setup
        // this is how to stub a method that returns VOID
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        //when - action or the behaviour that we are going to test
        employeeService.deleteEmployee(employee.getId());

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employee.getId());

    }


}
