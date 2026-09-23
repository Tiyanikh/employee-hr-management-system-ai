package com.portfolio.hr.repository;

import com.portfolio.hr.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByWorkDateBetweenOrderByWorkDateDesc(LocalDate from, LocalDate to);
}
