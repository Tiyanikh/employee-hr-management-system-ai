package com.portfolio.hr.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate workDate;

    private LocalDateTime clockIn;
    private LocalDateTime clockOut;

    @Column(nullable = false)
    private String status;

    public Attendance() {}

    public Long getId() { return id; }
    public Employee getEmployee() { return employee; }
    public LocalDate getWorkDate() { return workDate; }
    public LocalDateTime getClockIn() { return clockIn; }
    public LocalDateTime getClockOut() { return clockOut; }
    public String getStatus() { return status; }
    public void setId(Long id) { this.id = id; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }
    public void setClockIn(LocalDateTime clockIn) { this.clockIn = clockIn; }
    public void setClockOut(LocalDateTime clockOut) { this.clockOut = clockOut; }
    public void setStatus(String status) { this.status = status; }
}
