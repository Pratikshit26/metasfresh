/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class TimeBooking
{
	@Nullable
	TimeBookingId timeBookingId;

	@NonNull
	UserId performingUserId;

	@NonNull
	OrgId orgId;

	@NonNull
	IssueId issueId;

	long bookedSeconds;

	@NonNull
	String hoursAndMins;

	@NonNull
	Instant bookedDate;
}
