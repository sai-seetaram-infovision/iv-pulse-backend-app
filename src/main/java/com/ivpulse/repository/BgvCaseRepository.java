
package com.ivpulse.repository;

import com.ivpulse.entity.BgvCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BgvCaseRepository extends JpaRepository<BgvCase, Long> {

    @Query(value = """
        SELECT e.full_name, COALESCE(p.project_name,'—') AS project_name, b.status, b.started_on
        FROM bgv_case b
        JOIN employee e ON e.id = b.employee_id
        LEFT JOIN engagement p ON p.id = e.project_id
        WHERE LOWER(b.status) = 'in-progress'
        ORDER BY b.started_on DESC
    """, nativeQuery = true)
    Page<Object[]> findInProgress(Pageable pageable);
}
