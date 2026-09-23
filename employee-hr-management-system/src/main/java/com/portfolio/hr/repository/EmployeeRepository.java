package com.portfolio.hr.repository;

import com.portfolio.hr.entity.Employee;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);

    @Query("""
        select e from Employee e
        where lower(e.firstName) like lower(concat('%', :q, '%'))
           or lower(e.lastName) like lower(concat('%', :q, '%'))
           or lower(e.employeeNumber) like lower(concat('%', :q, '%'))
           or lower(e.email) like lower(concat('%', :q, '%'))
        order by e.lastName, e.firstName
    """)
    List<Employee> search(@Param("q") String q);

    List<Employee> findByDepartmentId(Long departmentId);
    long countByStatus(String status);
}
