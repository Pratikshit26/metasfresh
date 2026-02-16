package de.metas.phonecall;

import java.time.LocalDate;
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
public class PhonecallSchedule
{
	@NonNull
	PhonecallSchemaVersionLineId schemaVersionLineId;

	@Nullable
	PhonecallScheduleId id;
	
	@NonNull 
	OrgId orgId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	UserId contactId;

	@NonNull
	LocalDate date;

	@NonNull
	ZonedDateTime startTime;
	@NonNull
	ZonedDateTime endTime;

	boolean isOrdered;

	boolean isCalled;

	UserId salesRepId;

	@Nullable
	String description;

	public PhonecallSchemaId getPhonecallSchemaId()
	{
		return getSchemaVersionLineId().getVersionId().getPhonecallSchemaId();
	}

	public PhonecallSchemaVersionId getPhonecallSchemaVersionId()
	{
		return getSchemaVersionLineId().getVersionId();
	}

}
