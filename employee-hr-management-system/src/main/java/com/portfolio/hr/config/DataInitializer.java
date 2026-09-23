package com.portfolio.hr.config;

import com.portfolio.hr.entity.*;
import com.portfolio.hr.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(DepartmentRepository departments,
                           EmployeeRepository employees,
                           LeaveBalanceRepository balances,
                           LeaveRequestRepository leaves,
                           AttendanceRepository attendance,
                           PayrollRepository payrolls) {
        return args -> {
            Department it = departments.save(new Department("IT", "Software, infrastructure and support"));
            Department finance = departments.save(new Department("Finance", "Financial operations and reporting"));
            Department hr = departments.save(new Department("Human Resources", "People operations and employee services"));
            Department sales = departments.save(new Department("Sales", "Sales and client relationships"));

            Employee john = employee("EMP001", "John", "Doe", "john.doe@example.com",
                    "Software Developer", it, "65000", LocalDate.of(2024, 1, 15));
            Employee jane = employee("EMP002", "Jane", "Smith", "jane.smith@example.com",
                    "Financial Analyst", finance, "52000", LocalDate.of(2023, 6, 1));
            Employee mike = employee("EMP003", "Mike", "Jones", "mike.jones@example.com",
                    "HR Officer", hr, "48000", LocalDate.of(2022, 3, 10));
            Employee sarah = employee("EMP004", "Sarah", "Williams", "sarah.williams@example.com",
                    "Sales Executive", sales, "45000", LocalDate.of(2025, 2, 3));

            john = employees.save(john);
            jane = employees.save(jane);
            mike = employees.save(mike);
            sarah = employees.save(sarah);

            balances.save(new LeaveBalance(john, new BigDecimal("18")));
            balances.save(new LeaveBalance(jane, new BigDecimal("12")));
            balances.save(new LeaveBalance(mike, new BigDecimal("15")));
            balances.save(new LeaveBalance(sarah, new BigDecimal("20")));

            LeaveRequest approved = new LeaveRequest();
            approved.setEmployee(john);
            approved.setStartDate(LocalDate.now().plusDays(10));
            approved.setEndDate(LocalDate.now().plusDays(12));
            approved.setLeaveType("ANNUAL");
            approved.setReason("Family trip");
            approved.setStatus("APPROVED");
            leaves.save(approved);

            LeaveRequest pending = new LeaveRequest();
            pending.setEmployee(jane);
            pending.setStartDate(LocalDate.now().plusDays(5));
            pending.setEndDate(LocalDate.now().plusDays(6));
            pending.setLeaveType("ANNUAL");
            pending.setReason("Personal");
            pending.setStatus("PENDING");
            leaves.save(pending);

            Attendance a = new Attendance();
            a.setEmployee(john);
            a.setWorkDate(LocalDate.now());
            a.setClockIn(LocalDateTime.now().minusHours(8));
            a.setClockOut(LocalDateTime.now());
            a.setStatus("PRESENT");
            attendance.save(a);

            payroll(payrolls, john, "2026-09", "65000", "3500", "8500");
            payroll(payrolls, jane, "2026-09", "52000", "2200", "7000");
            payroll(payrolls, mike, "2026-09", "48000", "1500", "6200");
        };
    }

    private Employee employee(String number, String first, String last, String email,
                              String title, Department department, String salary, LocalDate hireDate) {
        Employee e = new Employee();
        e.setEmployeeNumber(number);
        e.setFirstName(first);
        e.setLastName(last);
        e.setEmail(email);
        e.setPhone("010-555-0100");
        e.setJobTitle(title);
        e.setDepartment(department);
        e.setBasicSalary(new BigDecimal(salary));
        e.setHireDate(hireDate);
        e.setStatus("ACTIVE");
        return e;
    }

    private void payroll(PayrollRepository repo, Employee employee, String period,
                         String basic, String allowance, String deduction) {
        Payroll p = new Payroll();
        p.setEmployee(employee);
        p.setPayPeriod(period);
        p.setBasicSalary(new BigDecimal(basic));
        p.setAllowances(new BigDecimal(allowance));
        p.setDeductions(new BigDecimal(deduction));
        p.setNetSalary(new BigDecimal(basic).add(new BigDecimal(allowance)).subtract(new BigDecimal(deduction)));
        p.setProcessedDate(LocalDate.now());
        repo.save(p);
    }
}
