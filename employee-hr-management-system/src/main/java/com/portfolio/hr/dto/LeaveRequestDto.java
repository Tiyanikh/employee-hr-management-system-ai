package com.portfolio.hr.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record LeaveRequestDto(
        @NotNull Long employeeId,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotBlank String leaveType,
        String reason
) {}
