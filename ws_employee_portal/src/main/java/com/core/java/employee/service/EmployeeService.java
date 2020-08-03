package com.core.java.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.entity.Employee;
import com.core.java.employee.mapper.EmployeeMapper;
import com.core.java.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeMapper employeeMapper;

	public EmployeeDTO doAddNewEmployee(EmployeeDTO employeeDTO) {
		Optional.ofNullable(employeeDTO).ifPresent(opt -> {
			Employee emp = employeeMapper.fromEmployeeDTO(employeeDTO);
			Employee employeeEntityResp = employeeRepository.save(emp);
			employeeDTO.setEmployeeID(employeeEntityResp.getEmployeeID());
		});
		return employeeDTO;
	}

	public List<EmployeeDTO> getAllEmployeeDetails() {
		List<EmployeeDTO> employeeDetailsResp = new ArrayList<EmployeeDTO>();
		List<Employee> employeeDetails = employeeRepository.findAll();
		if (Optional.ofNullable(employeeDetails).isPresent() && !employeeDetails.isEmpty()) {
			employeeDetailsResp = employeeDetails.stream().map(employee -> employeeMapper.fromEmployee(employee))
					.collect(Collectors.toList());
		}
		return employeeDetailsResp;
	}

	public void doRemoveEmployee(Integer employeeID) {
		employeeRepository.deleteById(employeeID);
	}

	public EmployeeDTO doUpdateEmployeeDetails(EmployeeDTO employeeDTO) {
		Optional.ofNullable(employeeDTO).ifPresent(opt -> {
			employeeRepository.save(employeeMapper.fromEmployeeDTO(employeeDTO));
		});
		return employeeDTO;
	}
}
