package com.core.java.employee.mapper;

import org.mapstruct.Mapper;

import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.entity.Employee;

@Mapper(componentModel="spring")
public interface EmployeeMapper {

	Employee fromEmployeeDTO(EmployeeDTO employeeDTO);
	
	EmployeeDTO fromEmployee(Employee employee);
	
	
}
