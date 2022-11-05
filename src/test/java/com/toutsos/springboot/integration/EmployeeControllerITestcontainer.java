package com.toutsos.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toutsos.springboot.model.Employee;
import com.toutsos.springboot.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// with this annotation spring creates a new context and copies all beans at this in order to be used for integration testing
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Testcontainers this annotation not need anymore because we manually start container from AbstractionBaseTest.class
public class EmployeeControllerITestcontainer  extends AbstractContainerBaseTest {

    //static in order to create container once and used from all tests
//    @Container
//    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    //In order to create an instance of this MySql container for other tests too
    // and put it in application context
//    @DynamicPropertySource
//    public static void dynamicPropertySource(DynamicPropertyRegistry registry){
//        registry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username",mySQLContainer::getUsername);
//        registry.add("spring.datasource.password",mySQLContainer::getPassword);
//    }

    //used to make HTTP requests
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObj_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        // given - precondition
        Employee employee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        // when - action or behavior to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result of output using assert statements
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        //given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Angelos").lastName("Toutsios").email("a.t@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Maria").lastName("KOntouri").email("m.k@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(listOfEmployees.size())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObj() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",employee.getId()));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }


    //negative scenario
    //JUnit for get employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnNotFoundStatus() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",1L));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObj() throws Exception {
        //given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Angelos2")
                .lastName("Toutsios2")
                .email("angelos.toutsios2@gmail.com")
                .build();

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));
    }

    //negative scenario
    //JUnit for update employee REST API
    @Test
    public void givenInvalidEmployeeId_whenUpdateEmployee_thenReturnNotFound() throws Exception {
        //given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Angelos2")
                .lastName("Toutsios2")
                .email("angelos.toutsios2@gmail.com")
                .build();

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //JUnit for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Angelos")
                .lastName("Toutsios")
                .email("angelos.toutsios@gmail.com")
                .build();

        employeeRepository.save(savedEmployee);


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
