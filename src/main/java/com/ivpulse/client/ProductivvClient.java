package com.ivpulse.client;

import com.ivpulse.dto.productivv.*;

import java.time.LocalDate;
import java.util.List;

public interface ProductivvClient {
    List<CostCenterDto> getCostCenters();
    List<ProjectDto> getProjects();
    List<RoleRefDto> getRolesReference();
    List<ProjectRateDto> getProjectRates();

    List<EmployeeDto> getEmployees();
    List<EmployeeDto> getEmployees(LocalDate from, LocalDate to);

    List<ResourceAssignmentDto> getResources();

    List<HiringDto> getHiring();
    List<OnboardingDto> getOnboarding();

    List<BillingSnapshotDto> getBillingSnapshots();

    List<TimesheetDto> getTimesheets(LocalDate from, LocalDate to);
}
