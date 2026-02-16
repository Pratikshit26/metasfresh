package de.metas.security;

import de.metas.menu.AdMenuId;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.GenericPermissions;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class Role
{
	@NonNull
	RoleId id;

	@NonNull
	String name;
	String description;

	@Nullable RoleGroup roleGroup;

	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;
	boolean accessAllOrgs;
	boolean useUserOrgAccess;

	UserId supervisorId;

	@NonNull
	TableAccessLevel userLevel;

	@NonNull
	GenericPermissions permissions;

	@NonNull
	Constraints constraints;

	boolean manualMaintainance;

	//
	// Menu
	AdTreeId menuTreeId;
	AdMenuId rootMenuId;

	AdTreeId orgTreeId;

	boolean webuiRole;

	UserId updatedBy;

	public boolean isSystem() {return id.isSystem();}
}
