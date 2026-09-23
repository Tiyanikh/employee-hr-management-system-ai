package com.portfolio.hr;

import com.portfolio.hr.dto.LeaveRequestDto;
import com.portfolio.hr.entity.*;
import com.portfolio.hr.exception.BusinessRuleException;
import com.portfolio.hr.repository.*;
import com.portfolio.hr.service.LeaveService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaveServiceTest {

    @Test
    void shouldRejectLeaveWhenBalanceIsInsufficient() {
        LeaveRequestRepository requests = mock(LeaveRequestRepository.class);
        LeaveBalanceRepository balances = mock(LeaveBalanceRepository.class);
        EmployeeRepository employees = mock(EmployeeRepository.class);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setStatus("ACTIVE");

        when(employees.findById(1L)).thenReturn(Optional.of(employee));

        LeaveBalance balance = new LeaveBalance(employee, new BigDecimal("2"));
        when(balances.findByEmployeeId(1L)).thenReturn(Optional.of(balance));

        LeaveService service = new LeaveService(requests, balances, employees);

        LeaveRequestDto dto = new LeaveRequestDto(
                1L,
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 5),
                "ANNUAL",
                "Trip"
        );

        assertThrows(BusinessRuleException.class, () -> service.create(dto));
        verify(requests, never()).save(any());
    }
}
