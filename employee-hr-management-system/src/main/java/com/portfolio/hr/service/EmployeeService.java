package com.portfolio.hr.service;

import com.portfolio.hr.dto.EmployeeRequest;
import com.portfolio.hr.entity.*;
import com.portfolio.hr.exception.ResourceNotFoundException;
import com.portfolio.hr.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           LeaveBalanceRepository leaveBalanceRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public List<Employee> findAll(String q, Long departmentId) {
        List<Employee> result = (q != null && !q.isBlank())
                ? employeeRepository.search(q.trim())
                : employeeRepository.findAll();
        if (departmentId != null) {
            result = result.stream().filter(e -> e.getDepartment().getId().equals(departmentId)).toList();
        }
        return result;
    }

    public Employee find(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee " + id + " not found"));
    }

    @Transactional
    public Employee create(EmployeeRequest request) {
        departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        if (employeeRepository.findByEmailIgnoreCase(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Employee e = new Employee();
        e.setEmployeeNumber(request.employeeNumber());
        e.setFirstName(request.firstName());
        e.setLastName(request.lastName());
        e.setEmail(request.email());
        e.setPhone(request.phone());
        e.setJobTitle(request.jobTitle());
        e.setDepartment(departmentRepository.findById(request.departmentId()).orElseThrow());
        e.setBasicSalary(request.basicSalary());
        e.setHireDate(request.hireDate());
        e.setStatus("ACTIVE");

        Employee saved = employeeRepository.save(e);
        leaveBalanceRepository.save(new LeaveBalance(saved, new java.math.BigDecimal("20.00")));
        return saved;
    }

    @Transactional
    public Employee deactivate(Long id) {
        Employee e = find(id);
        e.setStatus("INACTIVE");
        return e;
    }
}
