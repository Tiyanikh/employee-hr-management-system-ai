package com.portfolio.hr.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String jobTitle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private BigDecimal basicSalary;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false)
    private String status;

    public Employee() {}

    public Long getId() { return id; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getJobTitle() { return jobTitle; }
    public Department getDepartment() { return department; }
    public BigDecimal getBasicSalary() { return basicSalary; }
    public LocalDate getHireDate() { return hireDate; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setDepartment(Department department) { this.department = department; }
    public void setBasicSalary(BigDecimal basicSalary) { this.basicSalary = basicSalary; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public void setStatus(String status) { this.status = status; }
}
