package com.portfolio.hr.controller;

import com.portfolio.hr.dto.EmployeeRequest;
import com.portfolio.hr.entity.Employee;
import com.portfolio.hr.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> all(@RequestParam(required = false) String q,
                              @RequestParam(required = false) Long departmentId) {
        return service.findAll(q, departmentId);
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return service.find(id);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PatchMapping("/{id}/deactivate")
    public Employee deactivate(@PathVariable Long id) {
        return service.deactivate(id);
    }
}
