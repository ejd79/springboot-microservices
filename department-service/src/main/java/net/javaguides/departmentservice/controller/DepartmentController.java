package net.javaguides.departmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Department Service - DepartmentController",
        description = "Department Controller Exposes REST APIs for Department-Service"
)
@RestController
@RequestMapping("api/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    // constructor based dependency injection

    @Operation(
            summary = "Save Department REST API",
            description = "Save Department REST API is used to save department object in database"
    )

    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // Build save department REST Api
    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Department REST API",
            description = "Get Department REST API is used to get department object from the database"
    )

    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build get department rest api
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("department-code") String departmentCode) {
        DepartmentDto departmentDto = departmentService.getDepartmentByCode(departmentCode);
//        return ResponseEntity.ok(departmentDto);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }


    // Handle ResourceNotFoundException
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                        WebRequest webRequest) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "DEPARTMENT_NOT_FOUND"
//        );
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}
