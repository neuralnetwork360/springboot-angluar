package com.core.java.employee.mapper;

import com.core.java.employee.dto.EmployeeDTO;
import com.core.java.employee.entity.Employee;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-01T23:30:16+0530",
    comments = "version: 1.0.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee fromEmployeeDTO(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }
        
        Employee employee = new Employee();

        return employee;
    }
}
