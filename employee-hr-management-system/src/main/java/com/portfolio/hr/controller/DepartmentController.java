package com.portfolio.hr.controller;

import com.portfolio.hr.entity.Department;
import com.portfolio.hr.repository.DepartmentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Department> all() {
        return repository.findAll();
    }
}
