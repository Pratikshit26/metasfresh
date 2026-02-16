package de.metas.impex.api.impl;

import javax.annotation.Nullable;

import de.metas.impex.InputDataSourceId;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */
@Value
public class InputDataSourceQuery
{
OrgId orgId;

	ExternalId externalId;

	String internalName;

	InputDataSourceId inputDataSourceId;

	String value;

	@Builder(toBuilder = true)
	private InputDataSourceQuery(
			@NonNull final OrgId orgId,
			@Nullable final ExternalId externalId,
			@Nullable final String internalName,
			@Nullable final InputDataSourceId inputDataSourceId,
			@Nullable final String value)
	{
		this.orgId = orgId;
		this.externalId = externalId;
		this.internalName = internalName;
		this.inputDataSourceId = inputDataSourceId;
		this.value = value;
	}

}
