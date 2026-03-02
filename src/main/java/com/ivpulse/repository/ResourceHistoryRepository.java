
package com.ivpulse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ivpulse.entity.ResourceHistory;

public interface ResourceHistoryRepository extends JpaRepository<ResourceHistory, Long> {

    @Query(value = """
        WITH cte AS (
            SELECT
                r.employee_id,
                r.ref_type,
                r.created_date,
                LAG(r.created_date) OVER (PARTITION BY r.employee_id ORDER BY r.created_date) AS prev_created_date,
                LAG(r.ref_type) OVER (PARTITION BY r.employee_id ORDER BY r.created_date) AS prev_ref_type,
                r.reason
            FROM resource_history r
            WHERE r.ref_type IN ('RN','AN')
              AND r.is_billable = false
              AND r.is_active = true
        )
        SELECT e.full_name,
               COALESCE(p.project_name,'—') AS project_name,
               e.designation,
               EXTRACT(DAY FROM (c.created_date - c.prev_created_date)) AS date_diff,
               COALESCE(c.reason,'—') AS reason
        FROM cte c
        JOIN employee e ON e.id = c.employee_id
        LEFT JOIN engagement p ON p.id = e.project_id
        WHERE c.prev_ref_type = 'RN' AND c.ref_type = 'AN'
        ORDER BY e.full_name, c.created_date
        """,
        countQuery = "select 1",
        nativeQuery = true)
    Page<Object[]> findUnbilledTransitions(Pageable pageable);
}
