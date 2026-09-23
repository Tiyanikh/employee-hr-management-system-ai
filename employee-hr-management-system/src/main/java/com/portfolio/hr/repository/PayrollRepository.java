package com.portfolio.hr.repository;

import com.portfolio.hr.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findAllByOrderByProcessedDateDesc();
    List<Payroll> findByPayPeriod(String payPeriod);
}
