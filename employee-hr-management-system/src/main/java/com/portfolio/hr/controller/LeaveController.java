package com.portfolio.hr.controller;

import com.portfolio.hr.dto.LeaveRequestDto;
import com.portfolio.hr.entity.LeaveRequest;
import com.portfolio.hr.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {
    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeaveRequest> all() { return service.all(); }

    @PostMapping
    public LeaveRequest create(@Valid @RequestBody LeaveRequestDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PatchMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id) {
        return service.reject(id);
    }
}
