package com.portfolio.hr.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Employee employee;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal availableDays = BigDecimal.ZERO;

    public LeaveBalance() {}

    public LeaveBalance(Employee employee, BigDecimal availableDays) {
        this.employee = employee;
        this.availableDays = availableDays;
    }

    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public BigDecimal getAvailableDays() { return availableDays; }
    public void setId(Long id) { this.id = id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setAvailableDays(BigDecimal availableDays) { this.availableDays = availableDays; }
}
