package de.metas.phonecall;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class PhonecallSchemaVersionLine
{
	@Nullable
	PhonecallSchemaVersionLineId id;

	@NonNull
	OrgId orgId;

	@NonNull
	PhonecallSchemaVersionId versionId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	UserId contactId;

	@NonNull
	ZonedDateTime startTime;

	@NonNull
	ZonedDateTime endTime;

	@Nullable
	String description;
}
