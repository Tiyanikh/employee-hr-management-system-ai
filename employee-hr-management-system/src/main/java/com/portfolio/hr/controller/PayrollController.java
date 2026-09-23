package com.portfolio.hr.controller;

import com.portfolio.hr.dto.PayrollRequest;
import com.portfolio.hr.entity.Payroll;
import com.portfolio.hr.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    private final PayrollService service;

    public PayrollController(PayrollService service) {
        this.service = service;
    }

    @GetMapping
    public List<Payroll> all() { return service.all(); }

    @PostMapping
    public Payroll create(@Valid @RequestBody PayrollRequest dto) {
        return service.create(dto);
    }
}
