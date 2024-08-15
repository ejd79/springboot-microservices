package net.javaguides.employee_service.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.employee_service.dto.EmployeeDto;
import net.javaguides.employee_service.entity.Employee;
import net.javaguides.employee_service.exception.ResourceNotFoundException;
import net.javaguides.employee_service.repository.EmployeeRepository;
import net.javaguides.employee_service.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private ModelMapper modelMapper;

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

//        Employee employee = new Employee(
//                employeeDto.getId(),
//                employeeDto.getFirstName(),
//                employeeDto.getLastName(),
//                employeeDto.getEmail()
//        );
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);

//        EmployeeDto savedEmployeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
//                savedEmployee.getLastName(), savedEmployee.getEmail());
        EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);


        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
//        EmployeeDto employeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
//                savedEmployee.getLastName(), savedEmployee.getEmail());
        EmployeeDto employeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        return employeeDto;
    }
}
