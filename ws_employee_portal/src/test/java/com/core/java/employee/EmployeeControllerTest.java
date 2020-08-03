package com.core.java.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.core.java.employee.controller.EmployeeController;
import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@MockBean
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
		employeeDTO.setEmployeeID(1);
		Mockito.when(employeeService.doAddNewEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(employeeDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addemployee").accept(MediaType.APPLICATION_JSON)
				.content(addEmployee).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		EmployeeDTO employeeDTOResponse = objectMapper.readValue(response.getContentAsString(), EmployeeDTO.class);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertNotNull(employeeDTOResponse.getEmployeeID());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllEmployeeDetails() throws Exception {

		String employeeList = IOUtils.toString(this.getClass().getResourceAsStream("/json/employees.json"));
		List<EmployeeDTO> employeeDetailsList = objectMapper.readValue(employeeList,
				new TypeReference<List<EmployeeDTO>>() {
				});
		Mockito.when(employeeService.getAllEmployeeDetails()).thenReturn(employeeDetailsList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		List<EmployeeDTO> employeeDetailsResponseList = objectMapper.readValue(response.getContentAsString(),
				new TypeReference<List<EmployeeDTO>>() {
				});
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(employeeDetailsResponseList);
	}

	@Test
	public void testRemoveEmployee() throws Exception {
		Mockito.doNothing().when(employeeService).doRemoveEmployee(Mockito.any());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/removeemployee?empId=3");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateEmployeeDetails() throws Exception {

		String addEmployee = IOUtils.toString(this.getClass().getResourceAsStream("/json/updateemployee.json"));
		EmployeeDTO employeeDTO = objectMapper.readValue(addEmployee, EmployeeDTO.class);
		Mockito.when(employeeService.doUpdateEmployeeDetails(Mockito.any(EmployeeDTO.class))).thenReturn(employeeDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updateemployee").accept(MediaType.APPLICATION_JSON)
				.content(addEmployee).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		EmployeeDTO employeeDTOResponse = objectMapper.readValue(response.getContentAsString(), EmployeeDTO.class);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(employeeDTOResponse.getEmployeeID());
	}

}
