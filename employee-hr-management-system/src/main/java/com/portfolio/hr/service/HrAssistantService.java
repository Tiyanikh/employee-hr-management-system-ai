package com.portfolio.hr.service;

import com.portfolio.hr.entity.Employee;
import com.portfolio.hr.entity.LeaveRequest;
import com.portfolio.hr.entity.Payroll;
import com.portfolio.hr.repository.EmployeeRepository;
import com.portfolio.hr.repository.LeaveRequestRepository;
import com.portfolio.hr.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HrAssistantService {
    private final EmployeeRepository employees;
    private final LeaveRequestRepository leaves;
    private final PayrollRepository payrolls;

    public HrAssistantService(EmployeeRepository employees, LeaveRequestRepository leaves, PayrollRepository payrolls) {
        this.employees = employees;
        this.leaves = leaves;
        this.payrolls = payrolls;
    }

    public Map<String, Object> answer(String question) {
        String q = question == null ? "" : question.trim().toLowerCase(Locale.ROOT);
        if (q.isBlank()) {
            return response("Please enter a question about employees, leave or payroll.", "HELP");
        }

        if (containsAny(q, "active employees", "how many employees", "number of employees", "employee count")) {
            long active = employees.countByStatus("ACTIVE");
            long total = employees.count();
            return response("There are " + active + " active employees out of " + total + " employees in the system.", "EMPLOYEES");
        }

        if (containsAny(q, "pending leave", "pending requests", "leave requests waiting")) {
            long pending = leaves.countByStatus("PENDING");
            List<LeaveRequest> pendingRequests = leaves.findAllByOrderByStartDateDesc().stream()
                    .filter(l -> "PENDING".equalsIgnoreCase(l.getStatus()))
                    .limit(5).toList();
            String names = pendingRequests.stream()
                    .map(l -> l.getEmployee().getFirstName() + " " + l.getEmployee().getLastName())
                    .collect(Collectors.joining(", "));
            String detail = names.isBlank() ? "There are no pending leave requests." :
                    "There are " + pending + " pending leave request(s). The latest include: " + names + ".";
            return response(detail, "LEAVE");
        }

        if (containsAny(q, "payroll", "salary", "salaries", "total pay", "total payroll")) {
            BigDecimal total = payrolls.findAll().stream()
                    .map(Payroll::getNetSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return response("The recorded net payroll across processed records is " + money(total) + ".", "PAYROLL");
        }

        if (containsAny(q, "departments", "department count")) {
            long count = employees.findAll().stream().map(e -> e.getDepartment().getId()).distinct().count();
            return response("There are " + count + " departments represented by the current employees.", "DEPARTMENTS");
        }

        if (containsAny(q, "who works", "find employee", "employee named", "employees in")) {
            String found = findEmployeeAnswer(q);
            if (found != null) return response(found, "EMPLOYEE_SEARCH");
        }

        if (containsAny(q, "help", "what can you do", "examples")) {
            return response("Try asking: How many active employees are there? Who has pending leave? What is the total payroll? Which departments do we have? Or find an employee by name.", "HELP");
        }

        return response("I can help with employee counts, employee searches, pending leave and payroll information. Try: 'How many active employees are there?'", "HELP");
    }

    public Map<String, Object> insights() {
        long active = employees.countByStatus("ACTIVE");
        long pending = leaves.countByStatus("PENDING");
        BigDecimal payroll = payrolls.findAll().stream().map(Payroll::getNetSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Long> departmentCounts = employees.findAll().stream()
                .filter(e -> "ACTIVE".equalsIgnoreCase(e.getStatus()))
                .collect(Collectors.groupingBy(e -> e.getDepartment().getName(), Collectors.counting()));
        String largestDepartment = departmentCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("No department data");

        return Map.of(
                "activeEmployees", active,
                "pendingLeave", pending,
                "payrollTotal", payroll,
                "largestDepartment", largestDepartment,
                "summary", "There are " + active + " active employees, " + pending + " pending leave request(s), and " + money(payroll) + " in recorded net payroll."
        );
    }

    private String findEmployeeAnswer(String q) {
        String cleaned = q.replace("find employee", "").replace("employee named", "").replace("who works", "").trim();
        String[] words = cleaned.replaceAll("[^a-z0-9 ]", " ").trim().split("\\s+");
        if (words.length == 0) return null;
        List<Employee> matches = employees.findAll().stream()
                .filter(e -> containsIgnoreCase(e.getFirstName(), cleaned) || containsIgnoreCase(e.getLastName(), cleaned)
                        || containsIgnoreCase(e.getFirstName() + " " + e.getLastName(), cleaned)
                        || (words.length == 1 && (containsIgnoreCase(e.getFirstName(), words[0]) || containsIgnoreCase(e.getLastName(), words[0]))))
                .limit(5).toList();
        if (matches.isEmpty()) return "I could not find an employee matching that name.";
        return matches.stream().map(e -> e.getFirstName() + " " + e.getLastName() + " — " + e.getJobTitle() + " in " + e.getDepartment().getName()).collect(Collectors.joining("; ")) + ".";
    }

    private boolean containsIgnoreCase(String value, String query) {
        return value != null && query != null && value.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT));
    }

    private boolean containsAny(String text, String... values) {
        for (String value : values) if (text.contains(value)) return true;
        return false;
    }

    private Map<String, Object> response(String answer, String category) {
        return Map.of("answer", answer, "category", category);
    }

    private String money(BigDecimal amount) {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-ZA")).format(amount == null ? BigDecimal.ZERO : amount);
    }
}
