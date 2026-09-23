package com.portfolio.hr.controller;

import com.portfolio.hr.repository.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;
    private final LeaveRequestRepository leaves;
    private final PayrollRepository payrolls;

    public DashboardController(EmployeeRepository employees, DepartmentRepository departments,
                               LeaveRequestRepository leaves, PayrollRepository payrolls) {
        this.employees = employees;
        this.departments = departments;
        this.leaves = leaves;
        this.payrolls = payrolls;
    }

    @GetMapping
    public Map<String, Object> dashboard() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("employees", employees.count());
        data.put("activeEmployees", employees.countByStatus("ACTIVE"));
        data.put("departments", departments.count());
        data.put("pendingLeave", leaves.countByStatus("PENDING"));
        data.put("payrollRecords", payrolls.count());
        BigDecimal payrollTotal = payrolls.findAll().stream()
                .map(p -> p.getNetSalary())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        data.put("payrollTotal", payrollTotal);
        return data;
    }
}
