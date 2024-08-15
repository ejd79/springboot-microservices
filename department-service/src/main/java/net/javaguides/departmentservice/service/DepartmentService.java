package net.javaguides.departmentservice.service;

import net.javaguides.departmentservice.dto.DepartmentDto;

import java.util.Optional;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentByCode(String code);
}
