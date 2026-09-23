package com.portfolio.hr.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PayrollRequest(
        @NotNull Long employeeId,
        @NotBlank String payPeriod,
        @NotNull @DecimalMin("0.00") BigDecimal allowances,
        @NotNull @DecimalMin("0.00") BigDecimal deductions
) {}
