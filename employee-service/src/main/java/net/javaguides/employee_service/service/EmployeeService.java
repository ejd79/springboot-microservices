package net.javaguides.employee_service.service;

import net.javaguides.employee_service.dto.APIResponseDto;
import net.javaguides.employee_service.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);
}
