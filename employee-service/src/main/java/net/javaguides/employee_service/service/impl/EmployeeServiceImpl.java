package net.javaguides.employee_service.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.javaguides.employee_service.dto.APIResponseDto;
import net.javaguides.employee_service.dto.DepartmentDto;
import net.javaguides.employee_service.dto.EmployeeDto;
import net.javaguides.employee_service.dto.OrganizationDto;
import net.javaguides.employee_service.entity.Employee;
import net.javaguides.employee_service.exception.EmailAlreadyExistsException;
import net.javaguides.employee_service.exception.ResourceNotFoundException;
import net.javaguides.employee_service.repository.EmployeeRepository;
import net.javaguides.employee_service.service.APIClient;
import net.javaguides.employee_service.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    //    private RestTemplate restTemplate;
    private WebClient webClient;
//    private APIClient apiClient;

    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        // check if already exists employeeDto.email
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if (optionalEmployee.isPresent()) {
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

//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        LOGGER.info("inside getEmployeeById() method");

        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        // using Rest Template
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + savedEmployee.getDepartmentCode(),
//                DepartmentDto.class);
//        DepartmentDto departmentDto = responseEntity.getBody();

        // using WebClient
        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + savedEmployee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        // using OpenFeign Client
//        DepartmentDto departmentDto = apiClient.getDepartment(savedEmployee.getDepartmentCode());

        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/" + savedEmployee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();


//        EmployeeDto employeeDto = new EmployeeDto(savedEmployee.getId(), savedEmployee.getFirstName(),
//                savedEmployee.getLastName(), savedEmployee.getEmail());

        EmployeeDto employeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);

        return apiResponseDto;
    }


    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {

        LOGGER.info("inside getDefaultDepartment() method");

        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development department");

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOrganizationCode("ORG_Default");
        organizationDto.setOrganizationDescription("Organización por defecto");
        organizationDto.setOrganizationName("Default Organization");

        EmployeeDto employeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);
        return apiResponseDto;
    }
}
