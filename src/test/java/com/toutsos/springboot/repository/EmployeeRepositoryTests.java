package com.toutsos.springboot.repository;

import com.toutsos.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Junit test for save employee operation
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //JUnit test for get all employees operaion
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for get Employee by Id operation
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnTheEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for get employee by email
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for update Employee operation
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test

        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setEmail("angelos.toutsios@gmail.com");
        employeeDB.setFirstName("Angelos");
        Employee updatedEmployee = employeeRepository.save(employeeDB);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("angelos.toutsios@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Angelos");
    }
}
