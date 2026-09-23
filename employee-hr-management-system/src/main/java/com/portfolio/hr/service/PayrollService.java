package com.portfolio.hr.service;

import com.portfolio.hr.dto.PayrollRequest;
import com.portfolio.hr.entity.*;
import com.portfolio.hr.exception.*;
import com.portfolio.hr.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollService {
    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;

    public PayrollService(PayrollRepository payrollRepository, EmployeeRepository employeeRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Payroll> all() {
        return payrollRepository.findAllByOrderByProcessedDateDesc();
    }

    public Payroll create(PayrollRequest dto) {
        Employee employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if ("INACTIVE".equals(employee.getStatus())) {
            throw new BusinessRuleException("Inactive employees cannot be processed for payroll");
        }

        BigDecimal net = employee.getBasicSalary()
                .add(dto.allowances())
                .subtract(dto.deductions());

        if (net.signum() < 0) {
            throw new BusinessRuleException("Deductions cannot exceed gross salary");
        }

        Payroll p = new Payroll();
        p.setEmployee(employee);
        p.setPayPeriod(dto.payPeriod());
        p.setBasicSalary(employee.getBasicSalary());
        p.setAllowances(dto.allowances());
        p.setDeductions(dto.deductions());
        p.setNetSalary(net);
        p.setProcessedDate(LocalDate.now());
        return payrollRepository.save(p);
    }
}
