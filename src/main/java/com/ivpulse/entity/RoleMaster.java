package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "role_master", indexes = @Index(name = "idx_role_active", columnList = "active_flag"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleMaster {
	@Id
	@Column(name = "role_id", columnDefinition = "uuid")
	private UUID roleId;

	@Column(name = "role_name", length = 200, nullable = false)
	private String roleName;

	@Column(name = "role_category", length = 200)
	private String roleCategory;

	@Column(name = "active_flag", nullable = false)
	private Boolean activeFlag;

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
}
