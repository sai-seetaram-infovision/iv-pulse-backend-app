package com.ivpulse.dto.productivv;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceHistory {

    @JsonProperty("ID")
    private UUID id;

    @JsonProperty("InstanceId")
    private int instanceId;

    @JsonProperty("EmployeeId")
    private int employeeId;

    @JsonProperty("CostCenterID")
    private int costCenterID;

    @JsonProperty("ProjectId")
    private int projectId;

    @JsonProperty("ReportingManagerID")
    private int reportingManagerID;

    @JsonProperty("RefType")
    private String refType;

    @JsonProperty("RefNo")
    private UUID refNo;

    @JsonProperty("EffectiveDate")
    private LocalDateTime effectiveDate;

    @JsonProperty("IsBillable")
    private boolean isBillable;

    @JsonProperty("BillingDate")
    private LocalDateTime billingDate;

    @JsonProperty("DesignationID")
    private UUID designationID;

    @JsonProperty("LevelID")
    private UUID levelID;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("AuditComment")
    private String auditComment;

    @JsonProperty("ResourceType")
    private int resourceType;

    @JsonProperty("CreatedDate")
    private LocalDateTime createdDate;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCostCenterID() {
        return costCenterID;
    }

    public void setCostCenterID(int costCenterID) {
        this.costCenterID = costCenterID;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getReportingManagerID() {
        return reportingManagerID;
    }

    public void setReportingManagerID(int reportingManagerID) {
        this.reportingManagerID = reportingManagerID;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public UUID getRefNo() {
        return refNo;
    }

    public void setRefNo(UUID refNo) {
        this.refNo = refNo;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isBillable() {
        return isBillable;
    }

    public void setBillable(boolean billable) {
        isBillable = billable;
    }

    public LocalDateTime getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDateTime billingDate) {
        this.billingDate = billingDate;
    }

    public UUID getDesignationID() {
        return designationID;
    }

    public void setDesignationID(UUID designationID) {
        this.designationID = designationID;
    }

    public UUID getLevelID() {
        return levelID;
    }

    public void setLevelID(UUID levelID) {
        this.levelID = levelID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}