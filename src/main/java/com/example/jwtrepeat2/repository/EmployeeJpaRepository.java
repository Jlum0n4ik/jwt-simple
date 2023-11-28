package com.example.jwtrepeat2.repository;

import com.example.jwtrepeat2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByUsername(String username);
}
