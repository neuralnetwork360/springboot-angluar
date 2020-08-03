package com.core.java.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.core.java.employee.entity.Employee;

@CrossOrigin(origins="http://localhost:4200")
@RepositoryRestResource(path="employee")
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
