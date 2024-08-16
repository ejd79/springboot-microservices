package net.javaguides.employee_service.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.employee_service.dto.APIResponseDto;
import net.javaguides.employee_service.dto.DepartmentDto;
import net.javaguides.employee_service.dto.EmployeeDto;
import net.javaguides.employee_service.entity.Employee;
import net.javaguides.employee_service.exception.EmailAlreadyExistsException;
import net.javaguides.employee_service.exception.ResourceNotFoundException;
import net.javaguides.employee_service.repository.EmployeeRepository;
import net.javaguides.employee_service.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private RestTemplate restTemplate;
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        // check if already exists employeeDto.email
       Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
       if (optionalEmployee.isPresent()){
           throw new EmailAlreadyExistsException("Email already exist for Employee");
       }

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
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        // testing Rest Template
        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + savedEmployee.getDepartmentCode(),
                DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody();


//        EmployeeDto employeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
//                savedEmployee.getLastName(), savedEmployee.getEmail());
        EmployeeDto employeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);


        return apiResponseDto;
    }
}
