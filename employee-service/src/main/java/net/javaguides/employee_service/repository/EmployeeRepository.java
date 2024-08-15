package net.javaguides.employee_service.repository;

import net.javaguides.employee_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
