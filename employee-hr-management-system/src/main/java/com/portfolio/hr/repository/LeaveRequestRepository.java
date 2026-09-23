package com.portfolio.hr.repository;

import com.portfolio.hr.entity.LeaveRequest;
import org.springframework.data.jpa.repository.*;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findAllByOrderByStartDateDesc();

    @Query("""
        select l from LeaveRequest l
        where l.employee.id = :employeeId
          and l.status = 'APPROVED'
          and l.startDate <= :endDate
          and l.endDate >= :startDate
    """)
    List<LeaveRequest> findOverlappingApproved(Long employeeId, LocalDate startDate, LocalDate endDate);

    long countByStatus(String status);
}
