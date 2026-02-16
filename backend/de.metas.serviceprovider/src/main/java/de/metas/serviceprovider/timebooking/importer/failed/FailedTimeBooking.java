/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking.importer.failed;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class FailedTimeBooking
{
	@NonNull
	OrgId orgId;

	@NonNull
	String externalId;

	@NonNull
	ExternalSystem externalSystem;

	@Nullable
	FailedTimeBookingId failedTimeBookingId;

	@Nullable
	String jsonValue;

	@Nullable
	String errorMsg;
}
