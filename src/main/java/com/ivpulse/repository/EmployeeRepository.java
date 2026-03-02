
package com.ivpulse.repository;

import com.ivpulse.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
        select e.* from employee e where e.is_active = true and e.is_billable = false
        """, nativeQuery = true)
    Page<Employee> findNonBillableActive(Pageable pageable);
}
