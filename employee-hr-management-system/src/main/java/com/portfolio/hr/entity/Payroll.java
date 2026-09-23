package com.portfolio.hr.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    @Column(nullable = false)
    private String payPeriod;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal basicSalary;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal allowances;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal deductions;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal netSalary;

    @Column(nullable = false)
    private LocalDate processedDate;

    public Payroll() {}

    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public String getPayPeriod() { return payPeriod; }
    public BigDecimal getBasicSalary() { return basicSalary; }
    public BigDecimal getAllowances() { return allowances; }
    public BigDecimal getDeductions() { return deductions; }
    public BigDecimal getNetSalary() { return netSalary; }
    public LocalDate getProcessedDate() { return processedDate; }
    public void setId(Long id) { this.id = id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setPayPeriod(String payPeriod) { this.payPeriod = payPeriod; }
    public void setBasicSalary(BigDecimal basicSalary) { this.basicSalary = basicSalary; }
    public void setAllowances(BigDecimal allowances) { this.allowances = allowances; }
    public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }
    public void setProcessedDate(LocalDate processedDate) { this.processedDate = processedDate; }
}
