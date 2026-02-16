/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.timebooking.importer;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@ToString( exclude = "authToken")
public class ImportTimeBookingsRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	String authToken;

	@NonNull
	LocalDate startDate;

	@NonNull
	LocalDate endDate;
}
