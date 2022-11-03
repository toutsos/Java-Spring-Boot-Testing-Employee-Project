package com.toutsos.springboot.repository;

import com.toutsos.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// disable IN memory support in order to test MySql integration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryITests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    public void setup(){
         employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadarate")
                .email("ramesh@gmail.com")
                .build();
    }

    //Junit test for save employee operation
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();

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
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();

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
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
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
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
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
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
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

    //JUnit test for delete employee operation
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(optionalEmployee).isEmpty();
    }

    //JUnit test for custom JPQL query
    @Test
    public void givenFirstnameAndLastname_whenFindByJPQL_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByJPQL(employee.getFirstName(),employee.getLastName());

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for custom JPQL query with named params
    @Test
    public void givenFirstnameAndLastname_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByJPQLNamedParams(employee.getFirstName(),employee.getLastName());

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for custom Native query
    @Test
    public void givenFirstnameAndLastname_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for custom Native query with named params
    @Test
    public void givenFirstnameAndLastname_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(),employee.getLastName());

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }
}
