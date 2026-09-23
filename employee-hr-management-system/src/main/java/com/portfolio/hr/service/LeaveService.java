package com.portfolio.hr.service;

import com.portfolio.hr.dto.LeaveRequestDto;
import com.portfolio.hr.entity.*;
import com.portfolio.hr.exception.*;
import com.portfolio.hr.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {
    private final LeaveRequestRepository requestRepository;
    private final LeaveBalanceRepository balanceRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveService(LeaveRequestRepository requestRepository,
                        LeaveBalanceRepository balanceRepository,
                        EmployeeRepository employeeRepository) {
        this.requestRepository = requestRepository;
        this.balanceRepository = balanceRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequest> all() {
        return requestRepository.findAllByOrderByStartDateDesc();
    }

    @Transactional
    public LeaveRequest create(LeaveRequestDto dto) {
        Employee employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if ("INACTIVE".equals(employee.getStatus())) {
            throw new BusinessRuleException("Inactive employees cannot request leave");
        }
        if (dto.endDate().isBefore(dto.startDate())) {
            throw new BusinessRuleException("End date cannot be before start date");
        }

        long daysLong = ChronoUnit.DAYS.between(dto.startDate(), dto.endDate()) + 1;
        BigDecimal days = BigDecimal.valueOf(daysLong);

        LeaveBalance balance = balanceRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        if (balance.getAvailableDays().compareTo(days) < 0) {
            throw new BusinessRuleException("Insufficient leave balance. Available: " + balance.getAvailableDays());
        }

        if (!requestRepository.findOverlappingApproved(employee.getId(), dto.startDate(), dto.endDate()).isEmpty()) {
            throw new BusinessRuleException("Leave dates overlap an existing approved leave request");
        }

        LeaveRequest r = new LeaveRequest();
        r.setEmployee(employee);
        r.setStartDate(dto.startDate());
        r.setEndDate(dto.endDate());
        r.setLeaveType(dto.leaveType());
        r.setReason(dto.reason());
        r.setStatus("PENDING");
        return requestRepository.save(r);
    }

    @Transactional
    public LeaveRequest approve(Long id) {
        LeaveRequest r = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        if (!"PENDING".equals(r.getStatus())) {
            throw new BusinessRuleException("Only pending requests can be approved");
        }

        long daysLong = ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()) + 1;
        BigDecimal days = BigDecimal.valueOf(daysLong);
        LeaveBalance balance = balanceRepository.findByEmployeeId(r.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));

        if (balance.getAvailableDays().compareTo(days) < 0) {
            throw new BusinessRuleException("Insufficient leave balance");
        }
        balance.setAvailableDays(balance.getAvailableDays().subtract(days));
        r.setStatus("APPROVED");
        return r;
    }

    @Transactional
    public LeaveRequest reject(Long id) {
        LeaveRequest r = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        if (!"PENDING".equals(r.getStatus())) {
            throw new BusinessRuleException("Only pending requests can be rejected");
        }
        r.setStatus("REJECTED");
        return r;
    }
}
