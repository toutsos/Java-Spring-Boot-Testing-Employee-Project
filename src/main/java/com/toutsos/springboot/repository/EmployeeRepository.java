package com.toutsos.springboot.repository;


import com.toutsos.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{

    Optional<Employee> findByEmail(String email);

    //Define custom querry using JPQL using index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    //Define custom querry using JPQL with named params
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName,@Param("lastName") String lastName);

    //Define custom querry using Native query
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2",
    nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    //Define custom querry using Native query with named params
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName",
            nativeQuery = true)
    Employee findByNativeSQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
