package com.toutsos.springboot.service;

import com.toutsos.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

Employee saveEmployee(Employee employee);
List<Employee> getAllEmployees();
Optional<Employee> getEmployeeById(long id);

Employee updateEmployee(Employee employee);

void deleteEmployee(long id);

}
