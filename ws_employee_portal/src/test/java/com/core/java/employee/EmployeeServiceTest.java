package com.core.java.employee;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockitoSession;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.entity.Employee;
import com.core.java.employee.mapper.EmployeeMapper;
import com.core.java.employee.repository.EmployeeRepository;
import com.core.java.employee.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	private ObjectMapper objectMapper;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Spy
	private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
	
	@InjectMocks
	private EmployeeService employeeService;

	@Before
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddNewEmployee() throws Exception {

		String addEmployee = IOUtils.toString(this.getClass().getResourceAsStream("/json/addemployee.json"));
		EmployeeDTO employeeDTO = objectMapper.readValue(addEmployee, EmployeeDTO.class);
		Employee employeeEntity = objectMapper.readValue(addEmployee, Employee.class);
		employeeEntity.setEmployeeID(1);
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employeeEntity);
		EmployeeDTO employeeDTOResponse = employeeService.doAddNewEmployee(employeeDTO);
		assertNotNull(employeeDTOResponse.getEmployeeID());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllEmployeeDetails() throws Exception {

		String employeeList = IOUtils.toString(this.getClass().getResourceAsStream("/json/employees.json"));
		List<Employee> employeeDetailsEntityList = objectMapper.readValue(employeeList,
				new TypeReference<List<Employee>>() {
				});
		Mockito.when(employeeRepository.findAll()).thenReturn(employeeDetailsEntityList);
		List<EmployeeDTO> employeeDTOResponse = employeeService.getAllEmployeeDetails();
		assertNotNull(employeeDTOResponse);
		assertEquals(2, employeeDTOResponse.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testDoRemoveEmployee() throws Exception {

		Mockito.doNothing().when(employeeRepository).deleteById(Mockito.any());;
		employeeService.doRemoveEmployee(1);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testDoUpdateEmployeeDetails() throws Exception {

		String updateEmployee = IOUtils.toString(this.getClass().getResourceAsStream("/json/updateemployee.json"));
		EmployeeDTO employeeDTO = objectMapper.readValue(updateEmployee, EmployeeDTO.class);
		Employee employeeEntity = objectMapper.readValue(updateEmployee, Employee.class);
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employeeEntity);
		EmployeeDTO employeeDTOResponse = employeeService.doUpdateEmployeeDetails(employeeDTO);
		assertNotNull(employeeDTOResponse.getEmployeeID());
	}
	
	

}
