package com.toutsos.springboot.service;

import com.toutsos.springboot.exception.ResourceNotFoundException;
import com.toutsos.springboot.model.Employee;
import com.toutsos.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl  implements EmployeeService{
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee already exists with given email "+employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

}
