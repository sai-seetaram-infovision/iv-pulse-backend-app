package com.ivpulse.bgv.store;

import com.ivpulse.kpi.dto.KpiFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BgvRepository {

    private final JdbcTemplate jdbc;

    public BgvRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /* =======================
       KPI COUNTS
       ======================= */

    public long countAll(KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(DISTINCT er.resource_id)
            FROM engagement_resource er
            JOIN engagement e ON e.engagement_id = er.engagement_id
            JOIN client c     ON c.client_id = e.client_id
            JOIN resource r   ON r.resource_id = er.resource_id
            LEFT JOIN bgv_status b ON b.resource_id = er.resource_id
            WHERE 1=1
        """);

        List<Object> args = new ArrayList<>();
        applyFilters(sql, args, f);

        return jdbc.queryForObject(sql.toString(), Long.class, args.toArray());
    }

    public long countByBucket(String bucket, KpiFilter f) {
        StringBuilder sql = new StringBuilder(baseBucketSql());
        List<Object> args = new ArrayList<>();

        args.add(bucket);
        applyFilters(sql, args, f);

        return jdbc.queryForObject(sql.toString(), Long.class, args.toArray());
    }

    private String baseBucketSql() {
        return """
            SELECT COUNT(1) FROM (
              SELECT DISTINCT er.resource_id,
                     CASE
                       WHEN COALESCE(b.status,'PENDING') ILIKE 'clear%' THEN 'CLEAR'
                       WHEN COALESCE(b.status,'PENDING') ILIKE 'in%progress%' THEN 'IN_PROGRESS'
                       WHEN COALESCE(b.status,'PENDING') ILIKE 'hold%'
                         OR COALESCE(b.status,'PENDING') ILIKE 'fail%' THEN 'HOLD_OR_FAIL'
                       WHEN b.verified_on IS NULL THEN 'PENDING'
                       ELSE 'PENDING'
                     END AS bucket
              FROM engagement_resource er
              JOIN engagement e ON e.engagement_id = er.engagement_id
              JOIN client c     ON c.client_id = e.client_id
              JOIN resource r   ON r.resource_id = er.resource_id
              LEFT JOIN bgv_status b ON b.resource_id = er.resource_id
              WHERE 1=1
            ) x
            WHERE bucket = ?
        """;
    }

    /* =======================
       BREAKDOWN
       ======================= */

    public List<Object[]> fetchBreakdown(
            KpiFilter f,
            int page,
            int size,
            String sortCol,
            String sortDir
    ) {

        String sortColumn = switch (sortCol == null ? "status" : sortCol.toLowerCase()) {
            case "employeename"   -> "employee_name";
            case "clientname"     -> "client_name";
            case "engagementname" -> "engagement_name";
            case "rolename"       -> "role_name";
            case "requestedon"    -> "requested_on";
            case "verifiedon"     -> "verified_on";
            default               -> "status";
        };

        String sortDirection =
                "ASC".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
            SELECT
                c.client_id, c.client_name,
                e.engagement_id, e.engagement_name,
                r.resource_id, r.employee_code, r.employee_name,
                rm.role_id, rm.role_name,
                er.location,
                b.bgv_vendor,
                COALESCE(b.status,'PENDING') AS status,
                b.requested_on,
                b.verified_on,
                b.remarks
            FROM engagement_resource er
            JOIN engagement e ON e.engagement_id = er.engagement_id
            JOIN client c     ON c.client_id = e.client_id
            JOIN resource r   ON r.resource_id = er.resource_id
            LEFT JOIN role_master rm ON rm.role_id = er.role_id
            LEFT JOIN bgv_status b   ON b.resource_id = er.resource_id
            WHERE 1=1
        """);

        List<Object> args = new ArrayList<>();
        applyFilters(sql, args, f);

        sql.append(" ORDER BY ")
           .append(sortColumn)
           .append(" ")
           .append(sortDirection)
           .append(" LIMIT ? OFFSET ?");

        args.add(size);
        args.add(page * size);

        return jdbc.query(
                sql.toString(),
                (rs, i) -> new Object[]{
                        rs.getString("client_id"),
                        rs.getString("client_name"),
                        rs.getString("engagement_id"),
                        rs.getString("engagement_name"),
                        rs.getString("resource_id"),
                        rs.getString("employee_code"),
                        rs.getString("employee_name"),
                        rs.getString("role_id"),
                        rs.getString("role_name"),
                        rs.getString("location"),
                        rs.getString("bgv_vendor"),
                        rs.getString("status"),
                        rs.getDate("requested_on") == null
                                ? null
                                : rs.getDate("requested_on").toLocalDate(),
                        rs.getDate("verified_on") == null
                                ? null
                                : rs.getDate("verified_on").toLocalDate(),
                        rs.getString("remarks")
                },
                args.toArray()
        );
    }

    public long countBreakdown(KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(1) FROM (
              SELECT DISTINCT er.resource_id
              FROM engagement_resource er
              JOIN engagement e ON e.engagement_id = er.engagement_id
              JOIN client c     ON c.client_id = e.client_id
              JOIN resource r   ON r.resource_id = er.resource_id
              LEFT JOIN bgv_status b ON b.resource_id = er.resource_id
              WHERE 1=1
        """);

        List<Object> args = new ArrayList<>();
        applyFilters(sql, args, f);

        sql.append(") x");

        return jdbc.queryForObject(sql.toString(), Long.class, args.toArray());
    }

    /* =======================
       COMMON FILTERS
       ======================= */

    private void applyFilters(StringBuilder sql, List<Object> args, KpiFilter f) {
        if (f == null) return;

        if (f.clientId() != null && !f.clientId().isBlank()) {
            sql.append(" AND c.client_id = ?");
            args.add(f.clientId());
        }

        if (f.engagementId() != null && !f.engagementId().isBlank()) {
            sql.append(" AND e.engagement_id = ?");
            args.add(f.engagementId());
        }

        if (f.roleId() != null && !f.roleId().isBlank()) {
            sql.append(" AND er.role_id = ?");
            args.add(f.roleId());
        }

        if (f.location() != null && !f.location().isBlank()) {
            sql.append(" AND COALESCE(er.location, '') = ?");
            args.add(f.location());
        }
    }
}
