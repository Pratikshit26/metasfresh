/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking.importer;

import de.metas.externalreference.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.timebooking.TimeBookingId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ImportTimeBookingInfo
{
	@Nullable
	TimeBookingId timeBookingId;

	@NonNull
	ExternalId<ExternalSystem> externalTimeBookingId;

	@NonNull
	UserId performingUserId;

	@NonNull
	IssueId issueId;

	long bookedSeconds;

	@NonNull
	Instant bookedDate;
}
