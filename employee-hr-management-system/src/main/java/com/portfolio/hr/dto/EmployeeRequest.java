package com.portfolio.hr.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank String employeeNumber,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        String phone,
        @NotBlank String jobTitle,
        @NotNull Long departmentId,
        @NotNull @DecimalMin("0.00") BigDecimal basicSalary,
        @NotNull LocalDate hireDate
) {}
