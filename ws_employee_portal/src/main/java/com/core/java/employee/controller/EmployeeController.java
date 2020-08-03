package com.core.java.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.service.EmployeeService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping(value = "/addemployee")
	public ResponseEntity<EmployeeDTO> addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
		EmployeeDTO employeeDTOResp = employeeService.doAddNewEmployee(employeeDTO);
		return employeeDTOResp.getEmployeeID() != null
				? new ResponseEntity<EmployeeDTO>(employeeDTOResp, HttpStatus.CREATED)
				: new ResponseEntity<EmployeeDTO>(employeeDTOResp, HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping(value = "/employees")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployeeDetails() {
		List<EmployeeDTO> employeeDetails = employeeService.getAllEmployeeDetails();
		return !employeeDetails.isEmpty() ? new ResponseEntity<List<EmployeeDTO>>(employeeDetails, HttpStatus.OK)
				: new ResponseEntity<List<EmployeeDTO>>(employeeDetails, HttpStatus.NO_CONTENT);
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/removeemployee")
	public ResponseEntity removeEmployee(@RequestParam Integer empId) {
		employeeService.doRemoveEmployee(empId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/updateemployee")
	public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@RequestBody EmployeeDTO employeeDTO) {
		return new ResponseEntity<EmployeeDTO>(employeeService.doUpdateEmployeeDetails(employeeDTO), HttpStatus.OK);
	}
}
