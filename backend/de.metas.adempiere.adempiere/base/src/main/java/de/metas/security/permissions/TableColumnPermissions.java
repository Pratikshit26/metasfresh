package de.metas.security.permissions;

 


import javax.annotation.concurrent.Immutable;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;

/**
 * {@link TableColumnResource}'s permission.
 * 
 * @author tsa
 *
 */
@Immutable
public final class TableColumnPermissions extends AbstractPermissions<TableColumnPermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private TableColumnPermissions(final Builder builder)
	{
		super(builder);
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder();
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	@Override
	protected TableColumnPermission noPermission()
	{
		return TableColumnPermission.NONE;
	}

	public boolean isColumnAccess(final int AD_Table_ID, final int AD_Column_ID, final Access access)
	{
		// If we were asked for a AD_Column_ID <= 0 then return false automatically
		// NOTE: this is the case of GridTable.addField which is checking for access to a generated field (not one that has AD_Column_ID binding).
		if (AD_Column_ID <= 0)
		{
			return false;
		}

		final TableColumnResource resource = TableColumnResource.of(AD_Table_ID, AD_Column_ID);
		return hasAccess(resource, access);
	}

	public static class Builder extends PermissionsBuilder<TableColumnPermission, TableColumnPermissions>
	{

		@Override
		protected TableColumnPermissions createPermissionsInstance()
		{
			return new TableColumnPermissions(this);
		}
	}
}
